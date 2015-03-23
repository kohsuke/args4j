package org.kohsuke.args4j.spi;

import java.util.List;

/**
 * Interface that can be instantiated to get default values.
 * @see Setter
 * @author Stephan Fuhrmann
 */
public interface Getter<T> {
    /**
     * Gets the current value of the property.
     *
     * <p>
     * A {@link Getter} object has an implicit knowledge about the property it's getting,
     * and the instance of the option bean.
     *
     * @return
     *      empty list or null if there's no current value.
     */
    List<T> getValueList();
}
