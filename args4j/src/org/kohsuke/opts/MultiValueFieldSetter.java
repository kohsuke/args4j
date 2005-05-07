package org.kohsuke.opts;

import org.kohsuke.IllegalAnnotationError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Setter} that sets multiple values to a collection {@link Field}.
 *
 * @author Kohsuke Kawaguchi
 */
final class MultiValueFieldSetter  implements Setter {
    private final Object bean;
    private final Field f;

    public MultiValueFieldSetter(Object bean, Field f) {
        this.bean = bean;
        this.f = f;
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
        Object o = f.get(bean);
        if(o==null) {
            o = new ArrayList();
            f.set(bean,o);
        }
        if(!(o instanceof List))
            throw new IllegalAnnotationError("type of "+f+" is not a List");

        ((List)o).add(value);
    }
}
