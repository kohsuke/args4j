package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class DoubleCmdLineItem extends AbstractCmdLineItemImpl {
    /**
     * Value of this option. 
     */
    public double value;
    
    /**
     * True if the option was explicitly set.
     */
    public boolean isSet = false;
    
    /**
     * @param optionName
     *      Option names like "foo" or "bar".
     */
    public DoubleCmdLineItem( String optionName, String description ) {
        this(optionName,description,0);
    }

    /**
     * @param optionName
     *      Option names like "foo" or "bar".
     */
    public DoubleCmdLineItem( String optionName, String description, double defaultValue ) {
        super(optionName,description);
        this.value = defaultValue;
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        value = params.getDoubleParameter(0);
        isSet = true;
        return 1;
    }

    public boolean isSet() {
        return isSet;
    }
    
    public boolean acceptsMultiValues() {
        return false;
    }

    public AntElementParser createAntElementParser(CmdLineParser parser, Object antProject, String name) throws CmdLineException {
        return null;
    }
}
