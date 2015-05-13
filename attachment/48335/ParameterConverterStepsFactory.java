package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.annotations.ParameterConverters;
import org.jbehave.scenario.steps.ParameterConverters.ParameterConverter;

public class ParameterConverterStepsFactory {

	private static class MethodParameterConverter implements ParameterConverter {
		private Object instance;
		private Method method;

		public MethodParameterConverter( Method method, Object instance ) {
			this.method = method;
			this.instance = instance;
		}
		
		public boolean accept(Type type) {
			return type.equals( method.getReturnType() );
		}

		public Object convertValue(String value, Type type) {
			try {
				return method.invoke(instance, value);
			} catch (Exception e) {
				throw new IllegalStateException( e );				
			}
		}
		
	}
	
    public CandidateSteps[] createCandidateSteps(Object... stepsInstances) {
        CandidateSteps[] candidateSteps = new CandidateSteps[stepsInstances.length];
        for (int i = 0; i < stepsInstances.length; i++) {
        	Object stepsInstance = stepsInstances[i];
        	
        	List<ParameterConverter> converters = new ArrayList<ParameterConverter>();
        	converters.addAll( createFromParameterConvertersAnnotation( stepsInstance ) );
        	converters.addAll( createFromParameterConverterAnnotation( stepsInstance ) );
        	
        	StepsConfiguration configuration = new StepsConfiguration( new org.jbehave.scenario.steps.ParameterConverters( 
        			converters.toArray( new ParameterConverter[ converters.size() ]) ) );      
        	
            candidateSteps[i] = new DetailedSteps( configuration, new Steps(configuration, stepsInstance), stepsInstance );
        }
        return candidateSteps;
    }

	/**
	 * Get parameter converters from the @ParameterConverters annotation
	 */
	@SuppressWarnings("unchecked")
	private List<ParameterConverter> createFromParameterConvertersAnnotation(
			Object stepsInstance) {
		List<ParameterConverter> converters = new ArrayList<ParameterConverter>();
		ParameterConverters annotation = 
    		stepsInstance.getClass().getAnnotation( ParameterConverters.class );
		if( annotation != null ) {
			for( int i = 0; i < annotation.value().length; i ++ ) {
				Class<?> converterClass = annotation.value()[ i ];
				try {
					if( ParameterConverter.class.isAssignableFrom( converterClass ) ) {
						converters.add( ((Class<ParameterConverter>)converterClass).newInstance() );
					} else {
						Object instance = converterClass.newInstance();
						List<ParameterConverter> methodConverters = createFromParameterConverterAnnotation(instance);
						if( methodConverters.isEmpty() ) {
							throw new Exception( "No method annotated with @ParameterConverter found !" );
						}
						converters.addAll( methodConverters );
					}
				} catch( Exception e ) {
					throw new IllegalArgumentException( "Can't use converter " + 
							converterClass + " : " + e.getMessage(), e );					
				}				
			}
			return converters;
		}		
		return converters;
	}

	/**
	 * Create parameter converters from methods annotated with @ParameterConverter
	 */
	private List<ParameterConverter> createFromParameterConverterAnnotation(
			Object stepsInstance) {
		List<ParameterConverter> converters = new ArrayList<ParameterConverter>();
		
		for( Method method : stepsInstance.getClass().getMethods() ) {
			if( method.isAnnotationPresent(org.jbehave.scenario.annotations.ParameterConverter.class)) {
				converters.add( new MethodParameterConverter(method, stepsInstance));
			}
		}
		
		return converters;
	}
}