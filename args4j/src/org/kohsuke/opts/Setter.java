package org.kohsuke.opts;

import org.kohsuke.CmdLineException;

/**
 * Abstraction of the value setter.
 *
 * @author Kohsuke Kawaguchi
 */
public interface Setter {
    /**
     * Adds/sets a value to the property of the option bean.
     *
     * <p>
     * A {@link Setter} object has an implicit knowledge about the property it's setting,
     * and the instance of the option bean.
     */
    void addValue(Object value) throws CmdLineException;
}
