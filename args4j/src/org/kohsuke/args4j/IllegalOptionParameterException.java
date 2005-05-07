package org.kohsuke.args4j;

/**
 * Signals an error in a parameter of an option.
 * 
 * <p>
 * The {@link #getMessage()} method returns a human-readable
 * localized description of the error message.
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class IllegalOptionParameterException extends CmdLineException {
    private String optionName;
    private String value;

    /**
     * 
     * @param optionName
     *      The name of the option whose parameter has a problem.
     * @param value
     *      The token that is causing the problem.
     */
    public IllegalOptionParameterException(String optionName,String value) {
        super(Messages.format("IllegalOptionParameterException",optionName,value));
        this.optionName = optionName;
        this.value = value;
    }
    
    /**
     * Returns the name of the option that caused a problem.
     * 
     * @return
     *      For example, if the command line is "-n abc" where the
     *      "-n" option expects a number, then this method returns
     *      "n". 
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * Returns the parameter of the option that caused a problem.
     * 
     * @return
     *      For example, if the command line is "-n abc" where the
     *      "-n" option expects a number, then this method returns
     *      "abc". 
     */
    public String getValue() {
        return value;
    }
}
