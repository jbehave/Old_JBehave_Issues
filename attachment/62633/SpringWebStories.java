package com.bignibou.bdd;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bignibou.bdd.steps.WebSteps;
import com.google.common.util.concurrent.MoreExecutors;

@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure(using = SeleniumConfiguration.class, pendingStepStrategy = FailingUponPendingStep.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true)
@UsingSpring(resources = { "classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/steps.xml" })
@UsingSteps(instances = { WebSteps.class })
public class SpringWebStories extends InjectableEmbedder {

	@Test
	public void run() {
		injectedEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
		injectedEmbedder().runStoriesAsPaths(storyPaths());
	}

	protected List<String> storyPaths() {
		String searchInDirectory = CodeLocations.codeLocationFromPath("src/test/java").getFile();
		return new StoryFinder().findPaths(searchInDirectory, Arrays.asList("**/*.story"), null);
	}
}