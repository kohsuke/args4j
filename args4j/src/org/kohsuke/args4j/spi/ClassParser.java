package org.kohsuke.args4j.spi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.IllegalAnnotationError;
import org.kohsuke.args4j.MetadataParser;
import org.kohsuke.args4j.Option;

public class ClassParser extends MetadataParser {

	@Override
	public void parse(Object bean) {
        // recursively process all the methods/fields.
        for( Class c=bean.getClass(); c!=null; c=c.getSuperclass()) {
            for( Method m : c.getDeclaredMethods() ) {
                Option o = m.getAnnotation(Option.class);
                if(o!=null) {
                	addArgument(m, o);
                }
                Argument a = m.getAnnotation(Argument.class);
                if(a!=null) {
                	addArgument(m, a);
                }
            }

            for( Field f : c.getDeclaredFields() ) {
                Option o = f.getAnnotation(Option.class);
                if(o!=null) {
                	addArgument(f, o);
                }
                Argument a = f.getAnnotation(Argument.class);
                if(a!=null) {
                	addArgument(f, a);
                }
            }
        }
        /* Not sure if this can happen ... but leave it here. */
        for (int i=0;i<getAnnotations().size();++i) {
        	if (getAnnotations().get(i)==null) {
                throw new IllegalAnnotationError("No argument annotation for index "+i);
        	}
        }
	}

}
