package org.kohsuke.args4j.opts;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.kohsuke.args4j.AntElementParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class FilesCmdLineItem extends AbstractCmdLineItemImpl {
    
    /**
     * Actual modifiable data storage.
     */
    protected final List _values;
    
    /**
     * Read-only view of the data store. List of {@link File}s.
     */
    public final List values; 
    
    
    public FilesCmdLineItem( String optionName, String description ) {
        this(optionName,description,new ArrayList());
    }
    
    public FilesCmdLineItem( String optionName, String description, List storage ) {
        super(optionName,description);
        this._values = storage;
        this.values = Collections.unmodifiableList(storage);
    }
    
    /**
     * Obtains the contents of {@link #values} as an array of {@link File}s.
     * 
     * @return
     *      Always return non-null array (the length may be 0)
     */
    public final File[] getFiles() {
        return (File[]) _values.toArray(new File[_values.size()]);
    }
    
    public boolean parseAntAttribute(CmdLineParser parser, Object antProject, String name, String value) throws CmdLineException {
        return false;
    }

    public AntElementParser createAntElementParser(CmdLineParser parser, final Object antProject, String name) throws CmdLineException {
        if( this.name.equals(name) ) {
            return new AntElementParser() {
                public Object getParser() {
                    return new FileSet();
                }

                public void onComplete(Object parser) {
                    FileSet fs = (FileSet)parser;
                    DirectoryScanner ds = fs.getDirectoryScanner((Project)antProject);
                    String[] includedFiles = ds.getIncludedFiles();
                    File baseDir = ds.getBasedir();
                    for (int j = 0; j < includedFiles.length; ++j) {
                        _values.add( new File(baseDir, includedFiles[j]) );
                    }
                }
                
            };
        } else {
            return null;
        }
    }

    public int parseArguments(CmdLineParser parser, Parameters params) throws CmdLineException {
        _values.add(new File(params.getParameter(0)));
        return 1;
    }
    
    public boolean acceptsMultiValues() {
        return true;
    }
}
