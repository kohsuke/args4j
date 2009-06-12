package org.kohsuke.args4j;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.kohsuke.args4j.spi.OptionHandler;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Argument of the command line.
 *
 * This works mostly like {@link Option} except the following differences.
 *
 * <ol>
 *  <li>Arguments have an index about their relative position on the command line.
 * </ol>
 * 
 * @author Kohsuke Kawaguchi
 * @author Mark Sinke
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD})
public @interface Argument {
    /**
     * See {@link Option#usage()}.
     */
    String usage() default "";
    /**
     * See {@link Option#metaVar()}.
     */
    String metaVar() default "";
    /**
     * See {@link Option#required()}.
     */
    boolean required() default false;

    /**
     * See {@link Option#handler()}.
     */
    Class<? extends OptionHandler> handler() default OptionHandler.class;

    int index() default 0;

    /**
     * See {@link Option#multiValued()}.
     */
    boolean multiValued() default false;
}
