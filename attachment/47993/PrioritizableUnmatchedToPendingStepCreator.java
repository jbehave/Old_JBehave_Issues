package org.jbehave.scenario.steps;

import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.definition.ScenarioDefinition;

import java.util.*;
import static java.util.Arrays.asList;

public class PrioritizableUnmatchedToPendingStepCreator implements StepCreator {

    public Step[] createStepsFrom(StoryDefinition storyDefinition, Stage stage, boolean embeddedStory, CandidateSteps... candidateSteps) {
        List<Step> steps = new ArrayList<Step>();
        for (CandidateSteps candidates : candidateSteps) {
            switch (stage) {
                case BEFORE:
                    steps.addAll(candidates.runBeforeStory(embeddedStory));
                    break;
                case AFTER:
                    steps.addAll(candidates.runAfterStory(embeddedStory));
                    break;
                default:
                    break;
            }
        }
        return steps.toArray(new Step[steps.size()]);
    }

    public Step[] createStepsFrom(ScenarioDefinition scenario, Map<String, String> tableRow,
            CandidateSteps... candidateSteps) {
        List<Step> steps = new ArrayList<Step>();

        addMatchedScenarioSteps(scenario, steps, tableRow, candidateSteps);
        addBeforeAndAfterScenarioSteps(steps, candidateSteps);

        return steps.toArray(new Step[steps.size()]);
    }

    private void addBeforeAndAfterScenarioSteps(List<Step> steps, CandidateSteps[] candidateSteps) {
        for (CandidateSteps candidates : candidateSteps) {
            steps.addAll(0, candidates.runBeforeScenario());
        }

        for (CandidateSteps candidates : candidateSteps) {
            steps.addAll(candidates.runAfterScenario());
        }
    }

    private void addMatchedScenarioSteps(ScenarioDefinition scenarioDefinition, List<Step> steps,
            Map<String, String> tableRow, CandidateSteps... candidateSteps) {
        for (String stringStep : scenarioDefinition.getSteps()) {
        	List<CandidateStep> prioritised = prioritise(candidateSteps, stringStep);			
            Step step = new PendingStep(stringStep);
            for (CandidateStep candidate : prioritised) {
                if (candidate.ignore(stringStep)) { // ignorable steps are added
                                                    // so they can be reported
                    step = new IgnorableStep(stringStep);
                    break;
                }
                if (candidate.matches(stringStep)) {
					System.out.println("Winner: " + candidate.getPatternAsString() + "\n");					
                    step = candidate.createFrom(tableRow, stringStep);
                    break;
                }
            }
            steps.add(step);
        }
    }

    private List<CandidateStep> prioritise(CandidateSteps[] candidateSteps, final String stringStep) {
        List<CandidateStep> steps = new ArrayList<CandidateStep>();
        for (CandidateSteps candidates : candidateSteps) {
            steps.addAll(asList(candidates.getSteps()));
        }

		System.out.println("\n" + stringStep);
		System.out.println("---------------------------------------------------------------------------");

        Collections.sort(steps, new Comparator<CandidateStep>() {
            public int compare(CandidateStep o1, CandidateStep o2) {
				String o1PatternForScoring = o1.getPatternAsString().replaceAll("\\s\\$\\w+\\s", " ").replaceAll("\\$\\w+", "");
				String o2PatternForScoring = o2.getPatternAsString().replaceAll("\\s\\$\\w+\\s", " ").replaceAll("\\$\\w+", "");
				String stringStepForComparison = stringStep.replaceFirst("^(Given|When|Then|And) ", "");
                Integer score1 = 0 - new LevenshteinDistance().calculate(o1PatternForScoring, stringStepForComparison);
				Integer score2 = 0 - new LevenshteinDistance().calculate(o2PatternForScoring, stringStepForComparison);
				System.out.println(o1PatternForScoring + "(" + score1 + ") vs " + o2PatternForScoring + "(" + score2 + ")");
                int result = score2.compareTo(score1);
				return result != 0 ? result : o2.getPriority().compareTo(o1.getPriority());
            }
        });

        return steps;
    }
}