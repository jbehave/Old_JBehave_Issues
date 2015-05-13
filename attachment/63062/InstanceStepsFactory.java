package org.jbehave.core.steps;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbehave.core.configuration.Configuration;

/**
 * Overriden to retain order of step instances.
 */
public class InstanceStepsFactory extends AbstractStepsFactory {

	private final Map<Class<?>, Object> stepsInstances = new HashMap<Class<?>, Object>();
	private final List<Class<?>> stepsTypes = new LinkedList<Class<?>>();

	public InstanceStepsFactory(Configuration configuration, Object... stepsInstances) {
		this(configuration, asList(stepsInstances));
	}

	public InstanceStepsFactory(Configuration configuration, List<Object> stepsInstances) {
		super(configuration);
		for (Object instance : stepsInstances) {
			this.stepsInstances.put(instance.getClass(), instance);
			this.stepsTypes.add(instance.getClass());
		}
	}

	@Override
	protected List<Class<?>> stepsTypes() {
		return stepsTypes;
	}

	@Override
	public Object createInstanceOfType(Class<?> type) {
		Object instance = stepsInstances.get(type);
		if (instance == null) {
			throw new StepsInstanceNotFound(type, this);
		}
		return instance;
	}

}
