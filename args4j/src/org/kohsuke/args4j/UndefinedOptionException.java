package org.kohsuke.args4j;

/**
 * Signals that an option is not defined. 
 * 
 * <p>
 * The {@link #getMessage()} method returns a human-readable
 * localized description of the error message.
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class UndefinedOptionException extends CmdLineException {
    private final String optionName;
    
    public UndefinedOptionException(String optionName) {
        super(Messages.format("UndefinedOptionException",optionName));
        this.optionName = optionName;
    }

    
    /**
     * Returns the name of the option that caused a problem.
     * 
     * @return
     *      For example, if the command line is "-n" where the
     *      "-n" option is not recognized, then this method returns
     *      "n". 
     */
    public String getOptionName() {
        return optionName;
    }
}
