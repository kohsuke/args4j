package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class StringsCmdLineItem extends AbstractCmdLineItemImpl {
    
    /**
     * Data storage.
     * The {@link #values} field is just a view of this {@link List}.
     */
    private final List _values;
    
    /** Read-only list of values. */
    public final List values;

    public StringsCmdLineItem( String name, String description ) {
        this(name,description,new ArrayList());
    }
    
    public StringsCmdLineItem( String name, String description, List storage ) {
        super(name,description);
        this._values = storage;
        this.values = Collections.unmodifiableList(_values);
    }
    
    public String[] getValues() {
        return (String[]) _values.toArray(new String[_values.size()]);
    }
    
    public boolean acceptsMultiValues() {
        return true;
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        _values.add(params.getParameter(0));
        return 1;
    }

    public AntElementParser createAntElementParser(final CmdLineParser parser, Object antProject, String name) throws CmdLineException {
        if( this.name.equals(name) ) {
            return new AntElementParser() {
                public Object getParser() {
                    return this;
                }

                /**
                 * Called by Ant by using reflection.
                 */
                public void setValue( String value ) {
                    try {
                        addArgument(parser,value);
                    } catch (CmdLineException e) {
                        ; // impossible
                    }
                }
                
                public void onComplete(Object parser) {
                }
            };
        } else {
            return null;
        }
    }

}
