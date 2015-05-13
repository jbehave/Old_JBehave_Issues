/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;



/**
 * Adapter to make a {@link Scenario} look like a {@link GivenUsingMiniMock}
 * so it can be used to set up a {@link Environment}
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class GivenScenario extends GivenUsingMiniMock {

    private final Scenario scenario;
    private boolean beforeEvent = true;

    public GivenScenario(Scenario scenario) {
        this.scenario = scenario;
    }
    
    public Scenario getScenario() {
        return scenario;
    }

    public void setUp(Environment environment) throws Exception {
        scenario.accept(new SetUpVisitor(environment));
    }

    public Context getContext() {
        return scenario.getContext();
    }
    public Event getEvent() {
        return scenario.getEvent();
    }
    public String getDescription() {
        return scenario.getDescription();
    }
    public Outcome getOutcome() {
        return scenario.getOutcome();
    }
    public Story getStory() {
        return scenario.getStory();
    }
    
    private class SetUpVisitor implements Visitor {
    	private Environment environment;
    	
    	public SetUpVisitor(Environment environment) {
    		this.environment = environment;
    	}
    	
        public void visit(Visitable visitable) {
            try {
                // accept the visitor
                if (visitable instanceof Given) {
                    visitGiven((Given) visitable);
                }
                else if (visitable instanceof Event) {
                    visitEvent((Event) visitable);
                    beforeEvent = false;
                }
                else if (visitable instanceof Expectation) {
                    visitExpectation((Expectation) visitable);
                }
            }
            catch (Exception e) {
                throw new NestedVerificationException("Execution failed for " + visitable, e);
            }
        }
        
        private void visitGiven(Given given) throws Exception {
            given.setUp(environment);
        }
        
        private void visitEvent(Event event) throws Exception {
            event.occurIn(environment);
        }
        
        private void visitExpectation(Expectation expectation) {
            if (beforeEvent) {
                expectation.setExpectationIn(environment);
            }
        }
    }
}
