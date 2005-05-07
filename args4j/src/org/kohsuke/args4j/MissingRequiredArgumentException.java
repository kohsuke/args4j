package org.kohsuke.args4j;

/**
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class MissingRequiredArgumentException extends CmdLineException {
    /**
     * Argument that was not satisified.
     */
    private final Argument argument;

    public MissingRequiredArgumentException() {
        this(null);
    }
        
    public MissingRequiredArgumentException(Argument argument) {
        super(
            argument==null
                ?Messages.format("MissingRequiredArgumentException.0")
                :Messages.format("MissingRequiredArgumentException.1",argument.getName()));
        this.argument = argument;
    }

    
    /**
     * Returns the argument that was not satisified.
     * 
     * @return
     *      can be null if the value is not given in the constructor.
     */
    public Argument getArgument() {
        return argument;
    }
}
