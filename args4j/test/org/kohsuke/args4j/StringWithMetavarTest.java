package org.kohsuke.args4j;

public class StringWithMetavarTest extends Args4JTestBase {
    @Override
    public Object getTestObject() {
        return new StringWithMetavar();
    }

    public void testSettingUsage() {
        args = new String[]{"-wrong-usage"};
        try {
            parser.parseArgument(args);
            fail("Doesnt detect wrong parameters.");
        } catch (CmdLineException e) {
            String expectedError = "\"-wrong-usage\" is not a valid option";
            String expectedUsage   = " -str METAVAR : set a string";
            String[] usageLines = getUsageMessage();
            String errorMessage = e.getMessage();
            assertUsageLength(1);
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
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
            String expectedUsage   = " -str METAVAR : set a string";
            String errorMessage = e.getMessage();
            String[] usageLines = getUsageMessage();
            assertUsageLength(1);
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
        }
    }

}