package org.kohsuke.args4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tests the AT sign that reads options from an external file.
 * @author Stephan Fuhrmann
 */
public class AtOptionTest extends Args4JTestBase<AtOption> {
    @Override
    public AtOption getTestObject() {
        return new AtOption();
    }

    public void testSimpleAt() throws IOException, CmdLineException {
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        args = new String[]{"@"+tmp.getAbsolutePath()};
        parser.parseArgument(args);
        
        assertEquals("foo", testObject.str);
        assertEquals(null, testObject.noUsage);
        
        tmp.delete();
    }
    
    public void testAtAfterOpts() throws IOException, CmdLineException {
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        args = new String[]{"-noUsage","lala", "@"+tmp.getAbsolutePath()};
        parser.parseArgument(args);
        
        assertEquals("foo", testObject.str);
        assertEquals("lala", testObject.noUsage);
        
        tmp.delete();
    }
    
    public void testAtBeforeOpts() throws IOException, CmdLineException {
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        args = new String[]{"@"+tmp.getAbsolutePath(), "-noUsage","lala"};
        parser.parseArgument(args);
        
        assertEquals("foo", testObject.str);
        assertEquals("lala", testObject.noUsage);
        
        tmp.delete();
    }
}
