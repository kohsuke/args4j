package org.kohsuke.args4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
        
        parser.getProperties().withAtSyntax(true);
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        args = new String[]{"@"+tmp.getAbsolutePath()};
        parser.parseArgument(args);
        
        assertEquals("foo", testObject.str);
        assertNull(testObject.noUsage);
        assertNull(testObject.arguments);
        
        tmp.delete();
    }
    
    public void testAtAfterOpts() throws IOException, CmdLineException {
        
        parser.getProperties().withAtSyntax(true);
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        args = new String[]{"-noUsage","lala", "@"+tmp.getAbsolutePath()};
        parser.parseArgument(args);
        
        assertEquals("foo", testObject.str);
        assertEquals("lala", testObject.noUsage);
        assertNull(testObject.arguments);
        
        tmp.delete();
    }
    
    public void testAtBeforeOpts() throws IOException, CmdLineException {
        
        parser.getProperties().withAtSyntax(true);
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        args = new String[]{"@"+tmp.getAbsolutePath(), "-noUsage","lala"};
        parser.parseArgument(args);
        
        assertEquals("foo", testObject.str);
        assertEquals("lala", testObject.noUsage);
        assertNull(testObject.arguments);
        
        tmp.delete();
    }
    
    public void testAtOptsWithBeingDisabled() throws IOException, CmdLineException {
        
        File tmp = File.createTempFile("atoption", null);
        PrintWriter printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();
        
        // this time the @-option gets not interpreted because
        // it's disabled
        args = new String[]{"-noUsage", "foo", "@"+tmp.getAbsolutePath()};
        parser.parseArgument(args);
        
        assertEquals("default", testObject.str);
        assertEquals("foo", testObject.noUsage);
        assertEquals(Arrays.asList(new String[] {"@"+tmp.getAbsolutePath()}), 
                Arrays.asList(testObject.arguments));
        
        tmp.delete();
    }    
}
