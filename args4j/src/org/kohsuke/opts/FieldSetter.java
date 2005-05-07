package org.kohsuke.opts;

import java.lang.reflect.Field;

/**
 * {@link Setter} that sets to a {@link Field}.
 *
 * @author Kohsuke Kawaguchi
 */
final class FieldSetter implements Setter {
    private final Field f;
    private final Object bean;

    public FieldSetter(Object bean, Field f) {
        this.bean = bean;
        this.f = f;
    }

    public void addValue(Object value) {
        try {
            f.set(bean,value);
        } catch (IllegalAccessException _) {
            // try again
            f.setAccessible(true);
            try {
                f.set(bean,value);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessError(e.getMessage());
            }
        }
    }
}
