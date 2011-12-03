package org.kohsuke.args4j;

import org.kohsuke.args4j.ExplicitBooleanOptionTest.BooleanOptionHolder;
import org.kohsuke.args4j.spi.ExplicitBooleanOptionHandler;

public class ExplicitBooleanOptionTest extends Args4JTestBase<BooleanOptionHolder> {

    public static class BooleanOptionHolder {
        @Option(name = "-booleanOpt", handler = ExplicitBooleanOptionHandler.class, usage = "Set a boolean value")
        boolean booleanOpt;
        
        @Option(name = "-nextArg")
        boolean nextArg;
    }

    @Override
    public BooleanOptionHolder getTestObject() {
        return new BooleanOptionHolder();
    }

    public void testSetBooleanTrue() throws CmdLineException {
        args = new String[] { "-booleanOpt", "true" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }

    public void testSetBooleanOn() throws CmdLineException {
        args = new String[] { "-booleanOpt", "on" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }
    
    public void testSetBooleanYes() throws CmdLineException {
        args = new String[] { "-booleanOpt", "yes" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }

    public void testSetBooleanTrueCaseInsensitive() throws CmdLineException {
        args = new String[] { "-booleanOpt", "tRuE" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }
    
    public void testSetBoolean1() throws CmdLineException {
        args = new String[] { "-booleanOpt", "1" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }

    public void testSetBooleanFalse() throws CmdLineException {
        args = new String[] { "-booleanOpt", "false" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanOpt);
    }

    public void testSetBooleanOff() throws CmdLineException {
        args = new String[] { "-booleanOpt", "off" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanOpt);
    }
    
    public void testSetBooleanNo() throws CmdLineException {
        args = new String[] { "-booleanOpt", "no" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanOpt);
    }

    public void testSetBoolean0() throws CmdLineException {
        args = new String[] { "-booleanOpt", "0" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanOpt);
    }

    public void testSetBooleanFalseCaseInsensitive() throws CmdLineException {
        args = new String[] { "-booleanOpt", "FaLsE" };
        parser.parseArgument(args);
        assertFalse(testObject.booleanOpt);
    }
    
    public void testSetBooleanLastArgIsTrue() throws CmdLineException {
        args = new String[] { "-booleanOpt" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }

    public void testSetBooleanWithoutParamIsTrue() throws CmdLineException {
        args = new String[] { "-booleanOpt", "-nextArg" };
        parser.parseArgument(args);
        assertTrue(testObject.booleanOpt);
    }
    
    public void testIllegalBoolean() {
        args = new String[] { "-booleanOpt", "ILLEGAL_BOOLEAN" };
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
            assertUsageContains("Usage message should contain '[VAL]'", "[VAL]");
        }
    }

}
