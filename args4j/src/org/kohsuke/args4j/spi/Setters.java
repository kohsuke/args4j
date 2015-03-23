package org.kohsuke.args4j.spi;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.kohsuke.args4j.CmdLineParser;

/**
 * Factory of {@link Setter}s.
 *
 * @author Kohsuke Kawaguchi
 */
public class Setters {
    
    private Setters() {
        // no instance allowed
    }
    
    public static Setter create(CmdLineParser parser, AccessibleObject fieldOrMethod, Object bean) {
        if (fieldOrMethod instanceof Method) {
            return new MethodSetter(parser,bean,(Method) fieldOrMethod);
        } else {
            return create((Field)fieldOrMethod,bean);
        }
    }

    public static Setter create(Field f, Object bean) {
        if (Modifier.isFinal(f.getModifiers()))
            throw new IllegalStateException(String.format("Cannot set value to a final field '%s'.", f.getDeclaringClass().getName()+"."+f.getName()));
        if(f.getType().isArray())
            return new ArrayFieldSetter(bean,f);
        if(List.class.isAssignableFrom(f.getType()))
            return new MultiValueFieldSetter(bean,f);
        else
            return new FieldSetter(bean,f);
    }
}
