/*
 * Created on 09-Dec-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.exception.VerificationException;

public class ExpectationBehaviour {
	
	// matching, invocation and verification behaviour for 
	// exact number on invocations only	(never and once)
	
	public void shouldMatchIfExpectingNever() throws Throwable {
		Expectation expectation = new Expectation(null, "test").never();
		Verify.that(expectation.matches("test", null));
	}

	public void shouldThrowVerificationExceptionOnInvokeIfExpectingNever() throws Throwable {
		Expectation expectation = new Expectation(null, "test").never();
		try {
			expectation.invoke(null, null, null);
		}	
		catch (VerificationException ex) {
			return;
		}
		Verify.impossible("Invoke should have throw VerificationException");
	}

	public void shouldVerifyOKIfExpectingNeverWithoutInvoking() throws Throwable {
		Expectation expectation = new Expectation(null, "test").never();
		expectation.verify();
	}

	public void shouldMatchIfExpectingOnce() throws Throwable {
		Expectation expectation = new Expectation(null, "test").once();
		Verify.that(expectation.matches("test", null));
	}

	public void shouldStillMatchAfterInvocationIfExpectingOnce() throws Throwable {
		Expectation expectation = new Expectation(null, "test").once();
		expectation.invoke(null, null, null);
		Verify.that(expectation.matches("test", null));
	}

	public void shouldThrowVerificationExceptionOnSecondInvokeIfExpectingOnce() throws Throwable {
		Expectation expectation = new Expectation(null, "test").once();
		expectation.invoke(null, null, null);
		try {
			expectation.invoke(null, null, null);
		}	
		catch (VerificationException ex) {
			return;
		}
		Verify.impossible("Invoke should have throw VerificationException");
	}
	
	public void shouldFailVerifyIfExpectingOnceWithoutInvoking() {
		Expectation expectation = new Expectation(null, "test").once();
		try {
			expectation.verify();
		}	
		catch (VerificationException ex) {
			return;
		}
		Verify.impossible("Verify should have throw VerificationException");
	}
	
	public void shouldVerifyOKIfExpectingOnceAfterInvokingOnce() throws Throwable {
		Expectation expectation = new Expectation(null, "test").once();
		expectation.invoke(null, null, null);
		expectation.verify();
	}
	
}
