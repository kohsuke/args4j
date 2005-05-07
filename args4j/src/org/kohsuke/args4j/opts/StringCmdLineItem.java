package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Option that takes a {@link String} as a parameter.
 * 
 * <p>
 * For example, you can parse "-mode RELEASE" or
 * "-mode DEBUG" into "RELEASE"/"DEBUG".
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class StringCmdLineItem extends AbstractCmdLineItemImpl {
    
    /**
     * Value of this option. 
     */
    public String value;
        
    public StringCmdLineItem( String optionName, String description ) {
        super(optionName,description);
    }

    public StringCmdLineItem( String optionName, String description, String defaultValue ) {
        super(optionName,description);
        this.value = defaultValue;
    }
    
    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        value = params.getParameter(0);
        return 1;
    }
    
    public boolean acceptsMultiValues() {
        return false;
    }

    public AntElementParser createAntElementParser(CmdLineParser parser, Object antProject, String name) throws CmdLineException {
        return null;
    }
}