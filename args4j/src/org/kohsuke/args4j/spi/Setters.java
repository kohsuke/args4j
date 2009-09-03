package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.MapSetter;
import org.kohsuke.args4j.CmdLineParser;

import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Factory of {@link Setter}s.
 *
 * @author Kohsuke Kawaguchi
 */
public class Setters {
    public static Setter create(CmdLineParser parser, AccessibleObject fieldOrMethod, Object bean) {
        if (fieldOrMethod instanceof Method) {
            return new MethodSetter(parser,bean,(Method) fieldOrMethod);
        } else {
            return create((Field)fieldOrMethod,bean);
        }
    }

    public static Setter create(Field f, Object bean) {
        if(List.class.isAssignableFrom(f.getType()))
            return new MultiValueFieldSetter(bean,f);
        else if(Map.class.isAssignableFrom(f.getType()))
            return new MapSetter(bean,f);
        else
            return new FieldSetter(bean,f);
    }
}
