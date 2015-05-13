/*
 * Copyright (c) 2011 IST - International Software Technology. All rights reserved.
 */
package biz.ist.atlas;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import biz.ist.atlas.BaseJUnitStory.JUnitReporterBuilder;
import biz.ist.atlas.BaseJUnitStory.SingleNewLineConverter;
import biz.ist.atlas.BaseJUnitStory.YMDDateConverter;
import com.moralesce.jbehave.JUnitStoryReporter;
import com.moralesce.jbehave.JUnitStoryRunner;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;
import org.junit.runner.RunWith;

@RunWith(JUnitStoryRunner.class)
@UsingEmbedder(embedder = Embedder.class, verboseFailures = true)
@Configure(storyReporterBuilder = JUnitReporterBuilder.class, pendingStepStrategy = FailingUponPendingStep.class, parameterConverters = {
		SingleNewLineConverter.class, YMDDateConverter.class })
public abstract class BaseJUnitStory {

	public static class JUnitReporterBuilder extends StoryReporterBuilder {
		public JUnitReporterBuilder() {
			this.withDefaultFormats().withReporters(JUnitStoryReporter.getInstance()).withFailureTrace(true);
		}
	}

	public static class SingleNewLineConverter implements ParameterConverter {
		@Override
		public boolean accept(Type type) {
			return String.class.equals(type);
		}

		@Override
		public Object convertValue(String value, Type type) {
			// make sure only \n is used as a line separator
			// default code will use the system line separator
			return value.replace("\r", "");
		}
	}

	public static class YMDDateConverter extends DateConverter {
		public YMDDateConverter() {
			super(new SimpleDateFormat("yyyy-MM-dd"));
		}
	}
}