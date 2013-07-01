package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.IllegalAnnotationError;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * {@link Setter} that allows multiple values to be stored into one array field.
 *
 * <p>
 * Because of the {@link CmdLineParser} abstractions of allowing incremental parsing of options,
 * this implementation creates a whole new array each time a new value is found.
 *
 * This is also why we don't support a setter method that takes list/array as arguments.
 *
 * @author Kohsuke Kawaguchi
 */
final class ArrayFieldSetter implements Setter {
    private final Object bean;
    private final Field f;

    public ArrayFieldSetter(Object bean, Field f) {
        this.bean = bean;
        this.f = f;

        if(!f.getType().isArray())
            throw new IllegalAnnotationError(Messages.ILLEGAL_FIELD_SIGNATURE.format(f.getType()));
    }

    public FieldSetter asFieldSetter() {
        return new FieldSetter(bean,f);
    }

    public AnnotatedElement asAnnotatedElement() {
        return f;
    }

    public boolean isMultiValued() {
    	return true;
    }

    public Class getType() {
        return f.getType().getComponentType();
    }

    public void addValue(Object value) {
        try {
            doAddValue(bean, value);
        } catch (IllegalAccessException _) {
            // try again
            f.setAccessible(true);
            try {
                doAddValue(bean,value);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessError(e.getMessage());
            }
        }
    }

    private void doAddValue(Object bean, Object value) throws IllegalAccessException {
        Object ary = f.get(bean);
        if (ary == null) {
            if (value.getClass().isArray()) {
                ary = Array.newInstance(getType(), Array.getLength(value));
                System.arraycopy(value, 0, ary, 0, Array.getLength(value));
            } else {
                ary = Array.newInstance(getType(), 1);
                Array.set(ary, 0, value);
            }
        } else {
            int len = Array.getLength(ary);
            Object newAry;
            if(value.getClass().isArray()) {
                newAry = Array.newInstance(ary.getClass().getComponentType(), len + Array.getLength(value));
                System.arraycopy(ary, 0, newAry, 0, len);
                System.arraycopy(value, 0, newAry, len, Array.getLength(value));
            } else {
                newAry = Array.newInstance(ary.getClass().getComponentType(), len + 1);
                System.arraycopy(ary, 0, newAry, 0, len);
                Array.set(newAry, len, value);
            }
            ary = newAry;
        }

        f.set(bean, ary);
    }
}

