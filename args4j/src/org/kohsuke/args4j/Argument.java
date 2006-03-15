package org.kohsuke.args4j;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Argument of the command line.
 *
 * This works mostly like {@link Option} except the following differences.
 *
 * <ol>
 *  <li>Argument by definition doesn't have an option name.
 *  <li>Arguments are always allowed to occur multiple times.
 * </ol>
 * 
 * @author Kohsuke Kawaguchi
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD})
public @interface Argument {
}
