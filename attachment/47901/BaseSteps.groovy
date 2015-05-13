package uk.co.acuminous.bdd.behaviour.steps

import org.jbehave.scenario.steps.ParameterConverters
import org.jbehave.scenario.steps.Steps
import uk.co.acuminous.bdd.behaviour.converters.*
import uk.co.acuminous.bdd.Patient
import uk.co.acuminous.bdd.behaviour.pageobjects.Page
import uk.co.acuminous.bdd.behaviour.steps.TestContext
import org.jbehave.scenario.steps.CandidateStep
import java.lang.reflect.Method
import org.jbehave.scenario.steps.StepType
import org.jbehave.scenario.steps.StepsConfiguration
import uk.co.acuminous.bdd.jbehave.LazyCandidateStep
import uk.co.acuminous.bdd.jbehave.LazyCandidateStep

public abstract class BaseSteps extends Steps {

	public BaseSteps() {
		super(new ParameterConverters(
			new SpecialityConverter(),
			new PatientStateConverter(),
			new PatientConverter()
		))
	}

    protected CandidateStep createCandidateStep(Method method, StepType stepType, String stepPatternAsString, int priority, StepsConfiguration configuration) {
        return new LazyCandidateStep(stepPatternAsString, priority, stepType, method, this, configuration.getPatternBuilder(), configuration.getParameterConverters(), configuration.getStartingWordsByType());
    }

	Page getCurrentPage() {
		return TestContext.attributes.currentPage
	}

	void setCurrentPage(Page page) {
		TestContext.attributes.currentPage = page
	}

	Map getContext() {
		return TestContext.attributes
	}

}