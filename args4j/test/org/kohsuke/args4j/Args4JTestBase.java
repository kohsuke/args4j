package org.kohsuke.args4j;

import junit.framework.TestCase;


public abstract class Args4JTestBase extends TestCase {

    CmdLineParser parser;
    String[] args;
    Object testObject;

    public abstract Object getTestObject();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testObject = getTestObject();
        parser = new CmdLineParser(testObject);
    }

}