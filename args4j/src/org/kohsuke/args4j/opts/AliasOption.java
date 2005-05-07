package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineOption;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Option which is simply an alias to a set of other options. 
 * 
 * <p>
 * For example, you could define "-quiet" as "-verbose 0".
 * This implementation does nothing when used for Ant.
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class AliasOption implements CmdLineOption {
    
    private final String optionName;
    private final String[] realOptions;
    private Object description;
    
    public AliasOption( String optionName, String description, String[] realOptions ) {
        this.optionName = optionName;
        this.realOptions = realOptions;
        this.description =  Localizer.create(description);
    }
    
    public String getName() {
        return optionName;
    }
    
    public String getDescription() {
        if(description!=null)
            description = (String)description.toString();
        return (String)description;
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        parser.parse(realOptions);
        return 0;
    }

    public boolean parseAntAttribute(CmdLineParser parser, Object antProject, String name, String value) throws CmdLineException {
        return false;
    }

    public AntElementParser createAntElementParser(CmdLineParser parser, Object antProject, String name) throws CmdLineException {
        return null;
    }
}
