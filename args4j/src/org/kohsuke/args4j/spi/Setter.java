package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import java.lang.reflect.AnnotatedElement;

/**
 * Abstraction of the value setter.
 *
 * <p>
 * This abstracts away the difference between a field and a setter method, 
 * which object we are setting the value to,
 * and/or how we handle collection fields differently.
 * @see Getter
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
     * Whether this setter is intrinsically multi-valued.
     *
     * <p>
     * When parsing arguments (instead of options), intrinsically multi-valued setters consume
     * all the remaining arguments. So, if the setter can store multiple values, 
     * this method should return {@code true}.
     *
     * <p>
     * This characteristics of a setter doesn't affect option parsing at all; any options can be
     * specified multiple times.  In many cases, this is a no-op--but when your shell script expands
     * multiple environment variables (each of which may contain options), tolerating such 
     * redundant options can be useful.
     */
    boolean isMultiValued();

    /**
     * If this setter encapsulates a field, return the direct access to that field as
     * {@link FieldSetter}. This method serves two purposes:
     *
     * <ol>
     *   <li>This lets {@link OptionHandler}s bypass the collection/array handling of fields.
     *       This is useful if you're defining an option handler that produces array or collection
     *       from a single argument.</li>
     *   <li>The other is to retrieve the current value of the field (via {@link FieldSetter#getValueList()}).</li>
     * </ol>
     *
     * @return
     *      {@code null} if this setter wraps a method.
     */
    FieldSetter asFieldSetter();

    /**
     * Returns the {@link AnnotatedElement} by which you can access annotations written on this setter.
     *
     * This is the same {@link AnnotatedElement} that had {@link Option}/{@link Argument}.
     *
     * <p>
     * This enables {@link OptionHandler} to further tweak its behavior based on additional annotations.
     */
    AnnotatedElement asAnnotatedElement();
}
