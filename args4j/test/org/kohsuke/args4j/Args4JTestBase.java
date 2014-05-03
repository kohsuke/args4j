package org.kohsuke.args4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import junit.framework.TestCase;


/**
 * Base class for Args4J Tests.
 * It instantiates the test object, the CmdLineParser for
 * that test object and provides a String array for passing
 * to the parser.
 *
 * @author Jan Materne
 */
public abstract class Args4JTestBase<T> extends TestCase {

    CmdLineParser parser;
    String[] args;
    T testObject;
    
    private Locale defaultLocale;

    /**
     * Specifies which concrete object to return as test object.
     * @return the test object
     */
    public abstract T getTestObject();
    
    /**
     * Setter for args in a vararg manner.
     * @param args
     */
    public void setArgs(String... args) {
    	this.args = args;
    }
    
    /**
     * Returns the Locale to use for the tests.
     * Defaults to use the US-Locale but should be overwritten by classes
     * which tests I18N.          
     */         
    public Locale getLocale() {
        return Locale.US;
    }

    /**
     * Initializes the testObject and the parser for that object.
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        defaultLocale = Locale.getDefault();
        Locale.setDefault(getLocale());
        testObject = getTestObject();
        parser = createParser();
    }

    protected CmdLineParser createParser() {
        return new CmdLineParser(testObject);
    }

    /**
     * Restores the environment, namely the default Locale.
     */         
    @Override
    protected void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
    }

    /**
     * Checks the number of lines of the parsers usage message.
     * @param expectedLength
     * @see TestCase#assertEquals(String, int, int)
     * @see Args4JTestBase#getUsageMessage()
     */
    public void assertUsageLength(int expectedLength) {
        assertEquals("Wrong amount of lines in usage message", expectedLength, getUsageMessage().length);
    }
    
    /**
     * Asserts that a given text is part of the usage message.
     * @param message Error message if the text is not found.
     * @param containingText Text to search for.
     */
    public void assertUsageContains(String message, String containingText) {
    	boolean contains = false;
    	for (String line : getUsageMessage()) {
    		if (line.contains(containingText)) {
    			contains = true;
    			break;
    		}
    	}
    	if (!contains) {
    		throw new AssertionError(message);
    	}
    }

    /**
     * Extracts the usage message from the parser as String array.
     * @return the usage message
     * @see CmdLineParser#printUsage(OutputStream)
     */
    public String[] getUsageMessage() {
        Stream2String s2s = new Stream2String();
        parser.printUsage(s2s);
        return s2s.getString().split(System.getProperty("line.separator"));
    }
    
    protected void assertErrorMessagePrefix(String exectedPrefix, CmdLineException e) {
        String errorMessage = e.getMessage();
        assertTrue("Got wrong error message. Expected prefix: \""+exectedPrefix+"\", actual: \""+errorMessage+"\"", errorMessage.startsWith(exectedPrefix));
    }

    /**
     * Utility class for capturing an OutputStream into a String.
     * @author Jan Materne
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