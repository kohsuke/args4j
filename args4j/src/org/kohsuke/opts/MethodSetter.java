package org.kohsuke.opts;

import org.kohsuke.CmdLineException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * {@link Setter} that sets to a {@link Method}.
 *
 * @author Kohsuke Kawaguchi
 */
final class MethodSetter implements Setter {
    private final Object bean;
    private final Method m;

    public MethodSetter(Object bean, Method m) {
        this.bean = bean;
        this.m = m;
    }

    public void addValue(Object value) throws CmdLineException {
        try {
            try {
                m.invoke(bean,value);
            } catch (IllegalAccessException _) {
                // try again
                m.setAccessible(true);
                try {
                    m.invoke(bean,value);
                } catch (IllegalAccessException e) {
                    throw new IllegalAccessError(e.getMessage());
                }
            }
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if(t instanceof RuntimeException)
                throw (RuntimeException)t;
            if(t instanceof Error)
                throw (Error)t;
            if(t instanceof CmdLineException)
                throw (CmdLineException)t;

            // otherwise wrap
            if(t!=null)
                throw new CmdLineException(t);
            else
                throw new CmdLineException(e);
        }
    }
}
