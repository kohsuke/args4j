package org.kohsuke;

/**
 * Argument of the command line.
 *
 * This works mostly like {@link Option} except the following differences.
 *
 * <ol>
 *  <li>Argument by definition doesn't have an option name.
 *  <li>Arguments are always allowed to occur multiple times.
 *      (IOW, it works like {@link Option#repeatable()}==true.
 * </ol>
 * 
 * @author Kohsuke Kawaguchi
 */
public @interface Argument {
}
