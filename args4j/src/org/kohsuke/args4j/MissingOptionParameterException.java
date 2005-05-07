package org.kohsuke.args4j;

/**
 * Signals an error where an option is missing parameters.
 * 
 * <p>
 * The {@link #getMessage()} method returns a human-readable
 * localized description of the error message.
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class MissingOptionParameterException extends CmdLineException {
    private final String optionName;
    
    public MissingOptionParameterException(String optionName) {
        super(Messages.format("MissingOptionParameterException",optionName));
        this.optionName = optionName;
    }

    
    /**
     * Returns the name of the option that caused a problem.
     * 
     * @return
     *      For example, if the command line is "-n" where the
     *      "-n" option expects a number, then this method returns
     *      "n". 
     */
    public String getOptionName() {
        return optionName;
    }
}
