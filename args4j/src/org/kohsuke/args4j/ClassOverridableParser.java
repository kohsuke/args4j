package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.MethodSetter;
import org.kohsuke.args4j.spi.Setters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;


/**
 * Parser for analyzing Args4J annotations in the class hierarchy, allowing annotation declarations on overridden
 * members to override the annotations on the parents. The value of the overriding annotation is uses as-is; it is
 * not merged with parent bindings.
 *
 * This can be used to feed option bindings that span across multiple instances.
 *
 * @author Alex Lapins
 */
public class ClassOverridableParser extends ClassParser {
    @Override
    public void parse(Object bean, CmdLineParser parser) {
        HashSet<String> sf = new HashSet<>();
        HashSet<String> sm = new HashSet<>();
        for( Class c=bean.getClass(); c!=null; c=c.getSuperclass()) {
            for( Method m : c.getDeclaredMethods() ) {
                if (sf.contains(m.getName())) continue;
                Option o = m.getAnnotation(Option.class);
                if(o!=null) {
                    parser.addOption(new MethodSetter(parser,bean,m), o);
                }
                Argument a = m.getAnnotation(Argument.class);
                if(a!=null) {
                    parser.addArgument(new MethodSetter(parser,bean,m), a);
                }
                sf.add(m.getName());
            }

            for( Field f : c.getDeclaredFields() ) {
                if (sf.contains(f.getName())) continue;
                Option o = f.getAnnotation(Option.class);
                if(o!=null) {
                    parser.addOption(Setters.create(f,bean),o);
                }
                Argument a = f.getAnnotation(Argument.class);
                if(a!=null) {
                    parser.addArgument(Setters.create(f,bean), a);
                }
                sf.add(f.getName());
            }
        }
    }
}
