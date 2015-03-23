package org.kohsuke.args4j.spi;

/**
 * Interface that can be instantiated to get default values.
 * @see Setter
 * @author Stephan Fuhrmann
 */
public interface Getter<T> {
    /**
     * Gets the value of the property of the option bean.
     *
     * <p>
     * A {@link Getter} object has an implicit knowledge about the property it's getting,
     * and the instance of the option bean.
     */
    T getValue();
    
    /**
     * Formats the value for outputting it to the command line help.
     * @param value the value as returned by {@link #getValue()}
     * @return the human readable value representation.
     */
    String toString(T value);
}
