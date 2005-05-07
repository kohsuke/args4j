package org.kohsuke.args4j.opts;

import java.io.File;

import org.apache.tools.ant.Project;
import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * {@link Argument}/{@link CmdLineOption} that parses a {@link File} object.
 * 
 * <p>
 * When used in Ant, this option will become an attribute.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class FileCmdLineItem extends AbstractCmdLineItemImpl {
    
    /**
     * The actual parsed option value.
     */
    public File value;
    
    /**
     * @param optionName
     *      Option/argument names like "foo" or "bar".
     */
    public FileCmdLineItem( String optionName, String description ) {
        this(optionName,description,null);
    }
    
    /**
     * @param optionName
     *      Option/argument names like "foo" or "bar".
     */
    public FileCmdLineItem( String optionName, String description, File defaultValue ) {
        super(optionName,description);
        this.value = defaultValue;
    }
    
    public boolean parseAntAttribute( CmdLineParser parser, Object antProject, String name, String value)
        throws CmdLineException {
        
        if( !this.name.equals(name) )  return false;
        
        this.value = ((Project)antProject).resolveFile(value);
        return true;
    }

    public AntElementParser createAntElementParser(CmdLineParser parser, Object antProject, String name) throws CmdLineException {
        return null;
    }
    
    public boolean acceptsMultiValues() {
        return true;
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        value = new File(params.getParameter(0));
        return 1;
    }

}
