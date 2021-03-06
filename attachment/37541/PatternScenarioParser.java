package org.jbehave.scenario.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;

/**
 * Pattern-based scenario parser, which uses the configured keywords to find the
 * steps in the text scenarios.
 */
public class PatternScenarioParser implements ScenarioParser {

    private final Configuration configuration;

    public PatternScenarioParser() {
        this(new PropertyBasedConfiguration());
    }

    public PatternScenarioParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public StoryDefinition defineStoryFrom(String wholeStoryAsString) {
        Blurb blurb = parseBlurbFrom(wholeStoryAsString);
        List<ScenarioDefinition> scenarioDefinitions = parseScenariosFrom(wholeStoryAsString);
        return new StoryDefinition(blurb, scenarioDefinitions);
    }

    private List<ScenarioDefinition> parseScenariosFrom(String wholeStoryAsString) {
        List<ScenarioDefinition> scenarioDefinitions = new ArrayList<ScenarioDefinition>();
        List<String> scenarios = splitScenarios(wholeStoryAsString);
        for (String scenario : scenarios) {
            Matcher findingTitle = patternToPullScenarioTitlesIntoGroupOne().matcher(scenario);
            scenarioDefinitions.add(new ScenarioDefinition(findingTitle.find() ? findingTitle.group(1).trim() : "",
                    findSteps(scenario)));
        }
        return scenarioDefinitions;
    }

    private List<String> findSteps(String scenarioAsString) {
        Matcher matcher = patternToPullOutSteps().matcher(scenarioAsString);
        List<String> steps = new ArrayList<String>();
        int startAt = 0;
        while (matcher.find(startAt)) {
            steps.add(matcher.group(1));
            startAt = matcher.start(4);
        }

        return steps;
    }

    private Blurb parseBlurbFrom(String wholeStoryAsString) {
        Pattern findStoryBlurb = Pattern.compile("(.*?)(" + configuration.keywords().scenario() + ":).*",
                Pattern.DOTALL);
        Matcher matcher = findStoryBlurb.matcher(wholeStoryAsString);
        if (matcher.find()) {
            return new Blurb(matcher.group(1).trim());
        } else {
            return Blurb.EMPTY;
        }
    }

    private List<String> splitScenarios(String allScenariosInFile) {
        Pattern scenarioSplitter = patternToPullScenariosIntoGroupFour();
        Matcher matcher = scenarioSplitter.matcher(allScenariosInFile);
        int startAt = 0;
        List<String> scenarios = new ArrayList<String>();
        if (matcher.matches()) {
            while (matcher.find(startAt)) {
                scenarios.add(matcher.group(1));
                startAt = matcher.start(4);
            }
        } else {
            String loneScenario = allScenariosInFile;
            scenarios.add(loneScenario);
        }
        return scenarios;
    }

    private Pattern patternToPullScenariosIntoGroupFour() {
        return Pattern.compile(".*?((Scenario:) (.|\\s)*?)\\s*(\\Z|Scenario:).*".replace("Scenario", configuration
                .keywords().scenario()), Pattern.DOTALL);
    }

    private Pattern patternToPullScenarioTitlesIntoGroupOne() {
        String concatenatedKeywords = concatenateWithOr(configuration.keywords().given(), configuration.keywords()
                .when(), configuration.keywords().then(), configuration.keywords().others());
        return Pattern.compile(configuration.keywords().scenario() + ":(.*?)\\s*(" + concatenatedKeywords + ").*");
    }

    private String concatenateWithOr(String given, String when, String then, String[] others) {
    	return concatenateWithOr(false, given, when, then, others);
    }
    
    private String concatenateWithSpaceOr(String given, String when, String then, String[] others) {
        return concatenateWithOr(true, given, when, then, others);
    }
    
    private String concatenateWithOr(boolean usingSpace, String given, String when, String then, String[] others) {
        StringBuilder builder = new StringBuilder();
        builder.append(given).append(usingSpace ? "\\s|" : "|");
        builder.append(when).append(usingSpace ? "\\s|" : "|");
        builder.append(then).append(usingSpace ? "\\s|" : "|");
        builder.append(usingSpace ? concatenateWithSpaceOr(others) : concatenateWithOr(others));
        return builder.toString();
    }

    private String concatenateWithOr(String... keywords) {
        return concatenateWithOr(false, new StringBuilder(), keywords);
    }
    
    private String concatenateWithSpaceOr(String... keywords) {
    	return concatenateWithOr(true, new StringBuilder(), keywords);
    }

    private String concatenateWithOr(boolean usingSpace, StringBuilder builder, String[] keywords) {
        for (String other : keywords) {
            builder.append(other).append(usingSpace ? "\\s|" : "|");
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1); // chop off the last |
    }

    private Pattern patternToPullOutSteps() {
        String givenWhenThen = concatenateWithOr(configuration.keywords().given(), configuration.keywords().when(),
                configuration.keywords().then(), configuration.keywords().others());
        String givenWhenThenSpaced = concatenateWithSpaceOr(configuration.keywords().given(), configuration.keywords().when(),
        		configuration.keywords().then(), configuration.keywords().others());
        return Pattern.compile("((" + givenWhenThen + ") (.|\\s)*?)\\s*(\\Z|" + givenWhenThenSpaced + "|"
                + configuration.keywords().scenario() + ":)");
    }
}
