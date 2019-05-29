package org.kohsuke.args4j;

import org.kohsuke.args4j.ExplicitBooleanArgumentTest.BooleanArgumentHolder;
import org.kohsuke.args4j.spi.ExplicitBooleanOptionHandler;

public class ExplicitBooleanArgumentTest extends Args4JTestBase<BooleanArgumentHolder> {

    public static class BooleanArgumentHolder {
        @Argument(index = 0, handler = ExplicitBooleanOptionHandler.class, usage = "Set a boolean value")
        boolean booleanArg;
    }

    @Override
    public BooleanArgumentHolder getTestObject() {
        return new BooleanArgumentHolder();
    }

    public void testSetBooleanTrue() throws CmdLineException {
        args = new String[] { "true" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanArg);
    }

    public void testSetBooleanOn() throws CmdLineException {
        args = new String[] { "on" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanArg);
    }
    
    public void testSetBooleanYes() throws CmdLineException {
        args = new String[] { "yes" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanArg);
    }

    public void testSetBooleanTrueCaseInsensitive() throws CmdLineException {
        args = new String[] { "tRuE" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanArg);
    }
    
    public void testSetBoolean1() throws CmdLineException {
        args = new String[] { "1" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanArg);
    }

    public void testSetBooleanFalse() throws CmdLineException {
        args = new String[] { "false" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanArg);
    }

    public void testSetBooleanOff() throws CmdLineException {
        args = new String[] { "off" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanArg);
    }
    
    public void testSetBooleanNo() throws CmdLineException {
        args = new String[] { "no" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanArg);
    }

    public void testSetBoolean0() throws CmdLineException {
        args = new String[] { "0" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanArg);
    }

    public void testSetBooleanFalseCaseInsensitive() throws CmdLineException {
        args = new String[] { "FaLsE" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanArg);
    }
    
    public void testSetBooleanNoValueIsFalse() throws CmdLineException {
        args = new String[0];
        parser.parseArgument(args);
        assertFalse(testObject.booleanArg);
    }

    public void testIllegalBoolean() {
        args = new String[] { "ILLEGAL_BOOLEAN" };
        try {
            parser.parseArgument(args);
            fail("Can't set ILLEGAL_BOOLEAN as value.");
        } catch (CmdLineException expected) {}
    }

    public void testUsage() {
        args = new String[] { "-wrong" };
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            // This tests 2 things
            assertUsageContains("Usage message should contain '[true|false]'", "[true|false]");
        }
    }

}
