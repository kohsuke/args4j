package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineOption;
import org.kohsuke.args4j.Argument;

/**
 * {@link Argument}/{@link CmdLineOption} that parses a <tt>int</tt>.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class IntCmdLineItem extends AbstractCmdLineItemImpl {
    /**
     * Value of this option. 
     */
    public int value;
    
    /**
     * True if the option was explicitly set.
     */
    public boolean isSet = false;
        
    /**
     * @param optionName
     *      Option names like "foo" or "bar".
     */
    public IntCmdLineItem( String optionName, String description ) {
        this(optionName,description,0);
    }

    /**
     * @param optionName
     *      Option names like "foo" or "bar".
     */
    public IntCmdLineItem( String optionName, String description, int defaultValue ) {
        super(optionName,description);
        this.value = defaultValue;
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        value = params.getIntParameter(0);
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
