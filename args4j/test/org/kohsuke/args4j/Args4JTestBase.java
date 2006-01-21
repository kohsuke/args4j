package org.kohsuke.args4j;

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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testObject = getTestObject();
        parser = new CmdLineParser(testObject);
    }

}