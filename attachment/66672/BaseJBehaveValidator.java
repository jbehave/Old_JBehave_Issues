package com.here.had.bdd.stories;

import com.here.had.bdd.MyStoryReporter;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = false, stepsFactory = true, storyTimeoutInSecs = 9000000L)
@UsingSpring(resources = "classpath:spring-config.xml")

public abstract class BaseJBehaveValidator extends JUnitStories {
    private URL getStoryUrl() {
        URL storyURL = null;
        try {
            // This requires you to start Maven from the project directory
            storyURL = new URL("file://" + System.getProperty("user.dir")
                    + "/src/main/resources/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return storyURL;
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(
                        new LoadFromRelativeFile(getStoryUrl()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withReporters(new MyStoryReporter())
                                .withFormats(
                                        Format.CONSOLE,
                                        Format.HTML,
                                        Format.XML,
                                        Format.STATS))
            .usePendingStepStrategy(new FailingUponPendingStep());
    }

}
