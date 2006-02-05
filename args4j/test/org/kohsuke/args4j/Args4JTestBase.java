package org.kohsuke.args4j;

import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;


/**
 * Base class for Args4J Tests.
 * It instanstiates the test object, the CmdLineParser for
 * that test object and provides a String array for passing 
 * to the parser.
 * 
 * @author Jan Matèrne
 */
public abstract class Args4JTestBase extends TestCase {

    CmdLineParser parser;
    String[] args;
    Object testObject;

    /**
     * Specifies which concrete object to return as test object.
     * @return the test object
     */
    public abstract Object getTestObject();

    /**
     * Initializes the testObject and the parser for that object. 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testObject = getTestObject();
        parser = new CmdLineParser(testObject);
    }
    
    /**
     * Checks the number of lines of the parsers usage message.
     * @param expectedLength 
     * @see TestCase#assertEquals(String, int, int)
     * @see Args4JTestBase#getUsageMessage()
     */
    public void assertUsageLength(int expectedLength) {
    	assertEquals("Wrong amount of lines in usage message", getUsageMessage().length, expectedLength);
    }
    
    /**
     * Extracts the usage message from the parser as String array.
     * @return the usage message
     * @see CmdLineParser#printUsage(OutputStream)
     */
    public String[] getUsageMessage() {
    	Stream2String s2w = new Stream2String();
		parser.printUsage(s2w);
		return s2w.getString().split(System.getProperty("line.separator"));
    }
    
    /**
     * Utility class for capturing an OutputStream into a String.
     * @author Jan
     */
    private class Stream2String extends OutputStream {
    	private StringBuffer sb = new StringBuffer();
    	
		@Override
		public void write(int b) throws IOException {
			sb.append((char)b);
		}

		public String getString() {
			return sb.toString();
		}
    }

}