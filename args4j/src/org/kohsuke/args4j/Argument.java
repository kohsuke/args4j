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
    String usage() default "";
    String metaVar() default "";
    boolean required() default false;
    Class<? extends OptionHandler> handler() default OptionHandler.class;
    int index() default 0;
    boolean multiValued() default false;
}
