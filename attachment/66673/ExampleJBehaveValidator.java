package com.here.had.bdd.stories;

import com.here.had.bdd.steps.VssDecayTermination;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = false, stepsFactory = true, storyTimeoutInSecs = 9000000L)
@UsingSpring(resources = "classpath:spring-config.xml")
@UsingSteps(instances = {
    VssDecayTermination.class
})
public class ExampleJBehaveValidator extends BaseJBehaveValidator {
    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromPath("src/main/resources"),
            "stories/*.story", "");
    }
}
