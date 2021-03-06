/*
 * Created on 24-Dec-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.jbehave.core.UsingMocks;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.minilog.Log;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public abstract class AbstractScenarioVisitor implements Visitor {
    protected final Log log = Log.getLog(this);
	protected final String storyName;
	protected final Environment environment;
	protected boolean usedMocks = false;
	protected final List listeners = new ArrayList();
	
	public AbstractScenarioVisitor(String storyName, Environment environment) {
		this.storyName = storyName;
        this.environment = environment;
	}

	protected ScenarioResult giveSelfToScenario(Scenario scenario) {
		Throwable cause = null;
		try {
			scenario.accept(this);
		}

		catch (NestedVerificationException e) {
			cause = e.getCause();
		}

        final ScenarioResult result;
        if (cause == null && usedMocks) {
            result = new ScenarioResult(scenario.getDescription(), storyName, ScenarioResult.USED_MOCKS);
        }
        else {
            result = new ScenarioResult(scenario.getDescription(), storyName, cause);
        }
        return result;
	}
	
    public void visit(Visitable visitable) {
        try {
            // accept the visitor
            if (visitable instanceof Given) {
                visitGiven((Given) visitable);
            }
            else if (visitable instanceof Event) {
                visitEvent((Event) visitable);
            }
            else if (visitable instanceof Expectation) {
                visitExpectation((Expectation) visitable);
            }
        }
        catch (Exception e) {
            throw new NestedVerificationException("Execution failed for " + visitable, e);
        }
    }
    
    protected abstract void visitGiven(Given given) throws Exception ;
    protected abstract void visitEvent(Event event) throws Exception ;
    protected abstract void visitExpectation(Expectation expectation);

    protected void checkForMocks(UsingMocks component) {
        if (component.containsMocks()) {
            log.debug(component + " uses mocks!");
            usedMocks = true;
        }
    }

    public void addListener(ResultListener listener) {
    	// TODO - nothing currently done with listeners, and not in behaviour...
        listeners.add(listener);
    }
}
