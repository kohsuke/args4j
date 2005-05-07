package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineOption;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Partial {@link CmdLineOption}/{@link Argument} implementation.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public abstract class AbstractCmdLineItemImpl implements CmdLineOption, Argument {
    
    /**
     * option name / argument name.
     * 
     * For option names, this is a string like "-foo" or "-bar".
     * For argument names, this is a string like "foo" or "bar".
     * 
     * @see #getName()
     */
    protected final String name;
    
    /**
     * The description of this option/argument.
     * 
     * The {@link Object#toString()} method should be used
     * to obtain the actual textual representation.
     */
    private Object description;
    
    
    /**
     * @param name
     *      For option names, this is a string like "-foo" or "-bar".
     *      For argument names, this is a string like "foo" or "bar".
     */
    protected AbstractCmdLineItemImpl( String name, String description ) {
        this.name = name;
        this.description = Localizer.create(description);
    }

    public final String getName() {
        return name;
    }
    
    public String getDescription() {
        if(description!=null)
            description = description.toString();
        return (String)description;
    }
    

    /**
     * Default implementation uses the {@link #parseArguments()}.
     */
    public void addArgument(CmdLineParser parser, String arg) throws CmdLineException {
        parseArguments(parser,new ParametersImpl(new String[]{name,arg}));
    }

    /**
     * Default implementation uses the {@link #addArgument(CmdLineParser, String)} method.
     */
    public boolean parseAntAttribute(CmdLineParser parser, Object antProject, String name, String value) throws CmdLineException {
        if( this.name.equals(name) ) {
            addArgument(parser,value);
        }
        return false;
    }
}
