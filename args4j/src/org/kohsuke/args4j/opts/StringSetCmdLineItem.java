package org.kohsuke.args4j.opts;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * {@link CmdLineItem} that accepts multiple strings as a {@link Set}.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class StringSetCmdLineItem extends AbstractCmdLineItemImpl {
    
    /**
     * Data storage.
     * The {@link #values} field is just a view of this {@link Set}.
     */
    private final Set _values;
    
    /** Read-only list of values. */
    public final Set values;

    public StringSetCmdLineItem( String name, String description ) {
        this(name,description,new TreeSet());
    }
    
    public StringSetCmdLineItem( String name, String description, Set storage ) {
        super(name,description);
        this._values = storage;
        this.values = Collections.unmodifiableSet(_values);
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
    