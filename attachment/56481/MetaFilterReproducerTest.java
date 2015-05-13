package com.coremedia.test;

import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.model.Meta;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;

/**
 * Reproducer for JBEHAVE-583.
 */
public class MetaFilterReproducerTest {
  // ok
  @Test
  public void singleExclusion() {
    Meta meta = new Meta(Arrays.asList("environment all", "skip"));
    MetaFilter filter = new MetaFilter("-skip");
    assertFalse("should not be allowed", filter.allow(meta));
  }

  // fails, JBEHAVE-583
  @Test
  public void multipleExclusions() {
    Meta meta = new Meta(Arrays.asList("environment all", "skip"));
    MetaFilter filter = new MetaFilter("-environment preview -skip");
    assertFalse("should not be allowed", filter.allow(meta));
  }
}
