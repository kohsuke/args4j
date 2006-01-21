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
            //TODO check the correct usage message - needs return value from parser
            //String usageMessage = parser.printUsage(null).toString();
            String errorMessage = e.getMessage();
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
            //assertTrue("Got wrong usage message", usageMessage.startsWith(expectedUsage));
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
            //TODO check the correct usage message - needs return value from parser
            //String usageMessage = parser.printUsage(null).toString();
            String errorMessage = e.getMessage();
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
            //assertTrue("Got wrong usage message", usageMessage.startsWith(expectedUsage));
        }
    }

}