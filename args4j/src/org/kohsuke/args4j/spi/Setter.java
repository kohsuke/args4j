package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;

/**
 * Abstraction of the value setter.
 *
 * @author Kohsuke Kawaguchi
 */
public interface Setter<T> {
    /**
     * Adds/sets a value to the property of the option bean.
     *
     * <p>
     * A {@link Setter} object has an implicit knowledge about the property it's setting,
     * and the instance of the option bean.
     */
    void addValue(T value) throws CmdLineException;

    /**
     * Gets the type of the underlying method/field.
     */
    Class<T> getType();
    
    /**
     * Whether this setter is instrinsically multi-valued.
     */
    boolean isMultiValued();
}
