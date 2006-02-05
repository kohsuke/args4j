package org.kohsuke.args4j;

public class SimpleStringTest extends Args4JTestBase {

    @Override
    public Object getTestObject() {
        return new SimpleString();
    }

    public void testSettingStringNoValues() {
        SimpleString bo = (SimpleString)testObject;
        args = new String[]{};
        try {
            parser.parseArgument(args);
            assertTrue("Default value set.", "default".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Call without parameters is valid!");
        }
    }

    public void testSettingString() {
        SimpleString bo = (SimpleString)testObject;
        args = new String[]{"-str","test"};
        try {
            parser.parseArgument(args);
            assertTrue("Given value set.", "test".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Setting a string value should be possible");
        }
    }

    public void testSettingUsage() {
        args = new String[]{"-wrong-usage"};
        try {
            parser.parseArgument(args);
            fail("Doesnt detect wrong parameters.");
        } catch (CmdLineException e) {
            String expectedError = "\"-wrong-usage\" is not a valid option";
            String expectedUsage   = " -str VAL : set a string";
            String[] usageLines = getUsageMessage();
            String errorMessage = e.getMessage();
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
            assertUsageLength(1);
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
        }
    }

    public void testMissingParameter() {
        args = new String[]{"-str"};
        try {
            parser.parseArgument(args);
            fail("Should miss one parameter.");
        } catch (CmdLineException e) {
            String expectedError = "Option \"-str\" takes an operand";
            String expectedUsage   = " -str VAL : set a string";
            String[] usageLines = getUsageMessage();
            String errorMessage = e.getMessage();
            assertUsageLength(1);
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
        }
    }

}