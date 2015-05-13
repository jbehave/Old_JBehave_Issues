/*
 * Created on 22-12-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class GivenScenarioBehaviour extends UsingMiniMock {
	
	public void shouldSetUpGivenEventAndOutcomeExpectationsInEnvironment(){
		System.out.println("GivenScenarioBehaviour running");
		Mock givenMockA = mock(Given.class);
		Mock givenMockB = mock(Given.class);
		Mock eventMock = mock(Event.class);
		Mock expectationMockA = mock(Expectation.class);
		Mock expectationMockB = mock(Expectation.class);
		Mock environmentMock = mock(Environment.class);
		
		givenMockA.expects("setUp").with(environmentMock);
		givenMockB.expects("setUp").with(environmentMock);
		expectationMockA.expects("setExpectationIn").with(environmentMock);
		expectationMockB.expects("setExpectationIn").with(environmentMock);
		eventMock.expects("occurIn").with(environmentMock);
		
	    Context contextStub = new Context(new Given[]{
	    		(Given)givenMockA,
				(Given)givenMockB
	    });
	    
	    Outcome outcomeStub = new Outcome(new Expectation[]{
	    		(Expectation)expectationMockA,
				(Expectation)expectationMockB
	    });
	    
	    Event firstEvent = (Event)eventMock;
	    Event secondEvent = (Event)mock(Event.class);
	    
	    Story story = new Story(new Narrative("", "", ""),
	    		new AcceptanceCriteria());
	    
	    Scenario firstScenario = new ScenarioUsingMiniMock("first scenario",
	    		story,
				contextStub,
				firstEvent,
				outcomeStub);
	    
	    Scenario secondScenario = new ScenarioUsingMiniMock("second scenario",
	    		story,
				new Context(firstScenario, new Given[0]),
				secondEvent,
				new Outcome(new Expectation[]{}));
	    
	    secondScenario.accept(new Visitor(){
	    	public void visit(Visitable visitable) {}
	    });
	    
	    givenMockA.verify();
	    givenMockB.verify();
	    eventMock.verify();
	    expectationMockA.verify();
	    expectationMockB.verify();
	}
}
