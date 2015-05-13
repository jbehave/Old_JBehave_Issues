package uk.co.acuminous.bdd.jbehave

import org.jbehave.scenario.steps.CandidateStep
import org.jbehave.scenario.steps.Step
import java.lang.reflect.Method
import org.jbehave.scenario.steps.StepMonitor
import java.util.regex.Matcher
import org.jbehave.scenario.steps.StepResult
import org.jbehave.scenario.steps.StepType
import org.jbehave.scenario.steps.CandidateSteps
import org.jbehave.scenario.parser.StepPatternBuilder
import org.jbehave.scenario.steps.ParameterConverters

public class LazyCandidateStep extends CandidateStep implements Step {

	String stepAsString
	Map<String, String> tableRow
	Matcher matcher
	Method method
	StepMonitor stepMonitor
	String[] groupNames

	public LazyCandidateStep(String patternAsString, int priority, StepType stepType, Method method, CandidateSteps steps, StepPatternBuilder patternBuilder, ParameterConverters parameterConverters, Map<StepType, String> startingWords) {
		this(patternAsString, priority, stepType, method, (Object) steps, patternBuilder, parameterConverters, startingWords)
	}

	public LazyCandidateStep(String patternAsString, int priority, StepType stepType, Method method, Object stepsInstance, StepPatternBuilder patternBuilder, ParameterConverters parameterConverters, Map<StepType, String> startingWords) {
		super(patternAsString, priority, stepType, method, stepsInstance, patternBuilder, parameterConverters, startingWords)
	}


    StepResult perform() {
		println "creating step for $stepAsString"
		super.createStep(stepAsString, tableRow, matcher, method, stepMonitor, groupNames).perform()
	}

    StepResult doNotPerform() {
		super.createStep(stepAsString, tableRow, matcher, method, stepMonitor, groupNames).doNotPerform()
	}

    protected Step createStep(final String stepAsString, Map<String, String> tableRow, Matcher matcher,
                              final Method method, final StepMonitor stepMonitor, String[] groupNames) {

		this.stepAsString = stepAsString
		this.tableRow = tableRow
		this.matcher = matcher
		this.method = method
		this.stepMonitor = stepMonitor
		this.groupNames = groupNames
		return this
    }
}