package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.IllegalAnnotationError;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
final class ArrayFieldSetter implements Getter, Setter {
    private final Object bean;
    private final Field f;
    
    private Object defaultArray;

    public ArrayFieldSetter(Object bean, Field f) {
        this.bean = bean;
        this.f = f;

        if(!f.getType().isArray())
            throw new IllegalAnnotationError(Messages.ILLEGAL_FIELD_SIGNATURE.format(f.getType()));
        
        trySetDefault(bean);
    }

    /** Remember default so we throw away the default when adding user values.
     */
    private void trySetDefault(Object bean1) throws IllegalAccessError {
        try {
            doSetDefault(bean1);
        } catch (IllegalAccessException ex) {
            try {
                // try again
                f.setAccessible(true);
                doSetDefault(bean1);
            }catch (IllegalAccessException ex1) {
                throw new IllegalAccessError(ex1.getMessage());
            }
        }
    }
    
    private void doSetDefault(Object bean) throws IllegalAccessException {
        this.defaultArray = f.get(bean);        
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
        } catch (IllegalAccessException ex) {
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
        if (ary == null || ary == defaultArray) {
                ary = Array.newInstance(getType(), 1);
                Array.set(ary, 0, value);
        } else {
            int len = Array.getLength(ary);
            Object newAry = Array.newInstance(ary.getClass().getComponentType(), len +1);
                System.arraycopy(ary, 0, newAry, 0, len);
                Array.set(newAry, len, value);
            ary = newAry;
        }

        f.set(bean, ary);
    }

    public List<Object> getValueList() {
        f.setAccessible(true);
        try {
            List<Object> r = new ArrayList<Object>();


            // array element might be primitive, so Arrays.asList() won't always work
            Object array = f.get(bean);
            int len = Array.getLength(array);
            for (int i=0; i<len; i++)
                r.add(Array.get(array, i));

            return r;
        } catch (IllegalAccessException ex) {
            throw new IllegalAccessError(ex.getMessage());
        }
    }
}

