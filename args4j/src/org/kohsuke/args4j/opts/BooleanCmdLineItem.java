package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.IllegalOptionParameterException;

/**
 * {@link Argument}/{@link CmdLineOption} that parses a <tt>boolean</tt>.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class BooleanCmdLineItem extends AbstractCmdLineItemImpl {
    
    /**
     * The actual parsed option value.
     */
    public boolean value;
    
    /**
     * @param optionName
     *      Option/argument names like "foo" or "bar".
     */
    public BooleanCmdLineItem( String optionName, String description ) {
        super(optionName,description);
    }
    
    /**
     * @param optionName
     *      Option/argument names like "foo" or "bar".
     */
    public BooleanCmdLineItem( String optionName, String description, boolean defaultValue ) {
        super(optionName,description);
        this.value = defaultValue;
    }
    
    public AntElementParser createAntElementParser(CmdLineParser parser, Object antProject, String name) throws CmdLineException {
        return null;
    }
    
    /**
     * Returns true if this switch is on.
     */
    public final boolean isOn() {
        return value;
    }

    /**
     * Returns true if this switch is off.
     */
    public final boolean isOff() {
        return !value;
    }

    public void addArgument(String value) throws CmdLineException {
        if( value.equals("true") || value.equals("yes") || value.equals("1") ) {
            this.value = true;
            return;
        }
        
        if( value.equals("false") || value.equals("no") || value.equals("0") ) {
            this.value = false;
            return;
        }
        
        throw new IllegalOptionParameterException(name,value);
    }

    public boolean acceptsMultiValues() {
        return false;
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        value = true;
        return 0;
    }
}
