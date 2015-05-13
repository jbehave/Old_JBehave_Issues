package com.here.had.bdd;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jbehave.core.model.*;

public class MyStoryReporter implements org.jbehave.core.reporters.StoryReporter {

    @Override
    public void successful(String step) {
        System.out.println("[JBEHAVE]: Successful: " + step);
    }

    @Override
    public void ignorable(String s) {
        System.out.println("[JBEHAVE]: Ignorable: " + s);
    }

    @Override
    public void pending(String s) {
        System.out.println("[JBEHAVE]: Pending: " + s);
    }

    @Override
    public void notPerformed(String s) {
        System.out.println("[JBEHAVE]: Not performed: " + s);
    }

    @Override
    public void failed(String step, Throwable cause) {
        System.out.println("[JBEHAVE]: Failed:" + step);
        System.out.println("[JBEHAVE]: Failed reason:" + cause);
        System.out.println("[JBEHAVE]: Failed reason:" + cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void storyNotAllowed(Story story, String s) {
        System.out.println("[JBEHAVE]: Not allowed: " + story + " : " + s);
    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {
        System.out.println("[JBEHAVE]: Story Cancelled: " + story.getName() + " : " + storyDuration);
    }

    @Override
    public void beforeStory(Story story, boolean bool) {
        System.out.println("[JBEHAVE]: Before story: " + story + " : " + bool);
    }

    @Override
    public void afterStory(boolean b) {
        System.out.println("[JBEHAVE]: After story: " + b);
    }

    @Override
    public void narrative(Narrative narrative) {
        System.out.println("[JBEHAVE]: Narrative: " + narrative);
    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {
        System.out.println("[JBEHAVE]: Lifecycle: " + lifecycle);
    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String s) {
        System.out.println("[JBEHAVE]: Scenario Not Allowed: " + scenario + " : " + s);
    }

    @Override
    public void beforeScenario(String s) {
        System.out.println("[JBEHAVE]: Before scenario: " + s);
    }

    @Override
    public void scenarioMeta(Meta meta) {
        System.out.println("[JBEHAVE]: Scenario meta: " + meta);
    }

    @Override
    public void afterScenario() {
        System.out.println("[JBEHAVE]: After scenario");
    }

    @Override
    public void givenStories(GivenStories givenStories) {
        System.out.println("[JBEHAVE]: Given stories: " + givenStories);
    }

    @Override
    public void givenStories(List<String> strings) {
        System.out.println("[JBEHAVE]: Given stories: " + strings);
    }

    @Override
    public void beforeExamples(List<String> strings, ExamplesTable examplesTable) {
        System.out.println("[JBEHAVE]: Before examples: " + strings + " : " + examplesTable);
    }

    @Override
    public void example(Map<String, String> stringStringMap) {
        System.out.println("[JBEHAVE]: Example: " + stringStringMap);
    }

    @Override
    public void afterExamples() {
        System.out.println("[JBEHAVE]: After examples");
    }

    @Override
    public void beforeStep(String s) {
        System.out.println("[JBEHAVE]: Before step: " + s);
    }

    @Override
    public void failedOutcomes(String s, OutcomesTable outcome) {
        System.out.println("[JBEHAVE]: Failed outcomes: " + s + " : " + outcome);
    }

    @Override
    public void restarted(String s, Throwable throwable) {
        System.out.println("[JBEHAVE]: Restarted: " + s);
        System.out.println("[JBEHAVE]: Restarted reason: " + throwable);
        System.out.println("[JBEHAVE]: Restarted reason: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void dryRun() {
        System.out.println("[JBEHAVE]: Dry run");
    }

    @Override
    public void pendingMethods(List<String> strings) {
        System.out.println("[JBEHAVE]: Pending methods: " + strings);
    }
}
