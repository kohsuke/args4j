package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.MethodSetter;
import org.kohsuke.args4j.spi.Setters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Parser for analyzing Args4J annotations in the class hierarchy.
 *
 * This can be used to feed option bindings that span across multiple instances.
 *
 * @author Jan Materne
 */
public class ClassParser {
    public void parse(Object bean, CmdLineParser parser) {
        // recursively process all the methods/fields.
        for( Class c=bean.getClass(); c!=null; c=c.getSuperclass()) {
            for( Method m : c.getDeclaredMethods() ) {
                Option o = m.getAnnotation(Option.class);
                if(o!=null) {
                	parser.addOption(new MethodSetter(parser,bean,m), o);
                }
                Argument a = m.getAnnotation(Argument.class);
                if(a!=null) {
                    parser.addArgument(new MethodSetter(parser,bean,m), a);
                }
            }

            for( Field f : c.getDeclaredFields() ) {
                Option o = f.getAnnotation(Option.class);
                if(o!=null) {
                	parser.addOption(Setters.create(f,bean),o);
                }
                Argument a = f.getAnnotation(Argument.class);
                if(a!=null) {
                	parser.addArgument(Setters.create(f,bean), a);
                }
            }
        }
	}
}
