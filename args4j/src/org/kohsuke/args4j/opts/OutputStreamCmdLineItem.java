package org.kohsuke.args4j.opts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.tools.ant.Project;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Option used as an {@link OutputStream} or {@link Writer}.
 * 
 * <p>
 * The option takes one parameter and recognize that as a {@link java.io.File},
 * unless the parameter is '-', which is treated as <code>System.out</code>.
 * 
 * <p>
 * For example, users can write "-log build.log" and
 * you can open this file as an {@link OutputStream},
 * or they can write "-log -" to send it to console.
 * 
 * <p>
 * If the option is unspecified, this will create {@link java.io.OutputStream}
 * that just discard the input.
 * 
 * <p>
 * This class can be extended to set the default output to change
 * the interpretation of the unspecified state and "-". 
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class OutputStreamCmdLineItem extends StringCmdLineItem {

    private final class NullStream extends OutputStream {
        public void write(byte[] b, int off, int len) {}
        public void write(byte[] b) {}
        public void write(int b) {}
    }

    public OutputStreamCmdLineItem(String optionName,String description) {
        super(optionName,description,null);
    }

    public OutputStreamCmdLineItem(String optionName,String description,String defaultValue) {
        super(optionName,description,defaultValue);
    }

    
    /**
     * Opens the specified file for writing.
     * 
     * If the file is not specified, it returns a stream that's connected
     * to nothing.
     */
    public OutputStream createOutputStream() throws IOException {
        if(value==null)
            return createNullStream();
        
        if(value.equals("-"))
            return createConsoleStream();
        else
            return new FileOutputStream(value);
    }
    
    /**
     * Called when the option is not specified to create the default
     * {@link OutputStream}
     */
    protected OutputStream createNullStream() throws IOException {
        return new NullStream();
    }
    
    /**
     * Called when the option is '-' to create the {@link OutputStream}.
     */
    protected OutputStream createConsoleStream() throws IOException {
        return System.out;
    }
    
    /**
     * Opens the specified file for writing.
     * 
     * If the file is not specified, it returns a stream that's connected
     * to nothing.
     */
    public Writer createWriter() throws IOException {
        return new OutputStreamWriter(createOutputStream());
    }

    public boolean parseAntAttribute( CmdLineParser parser, Object antProject, String name, String value)
        throws CmdLineException {
        
        if( !super.name.equals(name) )  return false;
        
        this.value = ((Project)antProject).resolveFile(value).getPath();
        return true;
    }
}
