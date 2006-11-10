package org.kohsuke.args4j;

public class AliasedTest extends Args4JTestBase<Aliased> {
    @Override
    public Aliased getTestObject() {
        return new Aliased();
    }

    public void testMissingParameter() {
        args = new String[]{"-str"};
        try {
            parser.parseArgument(args);
            fail("Should miss one parameter.");
        } catch (CmdLineException e) {
            String expectedError = "Option \"-str (--long-str)\" takes an operand";
            String expectedUsage   = " -str (--long-str) METAVAR : set a string";
            String errorMessage = e.getMessage();
            String[] usageLines = getUsageMessage();
            assertUsageLength(1);
            assertTrue("Got wrong error message: " + errorMessage, errorMessage.startsWith(expectedError));
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
        }
    }
}
