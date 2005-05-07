package org.kohsuke.args4j;

/**
 * A command line argument.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public interface Argument extends CmdLineItem {
    
    /**
     * Called when an argument is specified.
     */
    void addArgument( CmdLineParser parser, String arg ) throws CmdLineException;
    
    /**
     * True if this {@link Argument} accepts multiple values.
     * 
     * <p>
     * If true, the {@link #addArgument(String)} method will be
     * called multiple times. Otherwise, the rest of the arguments
     * will be passed to successive {@link Argument}s.
     */
    boolean acceptsMultiValues();
}
