package org.jbehave.examples.trader.i18n;

import java.util.Locale;

import org.jbehave.examples.trader.i18n.steps.DeSteps;

public class DeStories extends LocalizedStories {
    
    @Override
    protected Locale locale() {
        return new Locale("de");
    }

    @Override
    protected String storyPattern() {
        // Acutally it would be "**/*.geschichte", but
        // we want to verify that umlauts work even here.
        return "**/*.geschüchte";
    }

    @Override
    protected Object localizedSteps() {
        return new DeSteps();
    }

}
