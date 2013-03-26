package org.kohsuke.args4j;

public class HiddenOptionTest extends Args4JTestBase<HiddenOption> {
    @Override
    public HiddenOption getTestObject() {
        return new HiddenOption();
    }

    public void testSettingUsage() {
        args = new String[]{"-wrong-usage"};
        try {
            parser.parseArgument(args);
            fail("Doesnt detect wrong parameters.");
        } catch (CmdLineException e) {
            String expectedError = "\"-wrong-usage\" is not a valid option";
            String expectedUsage = "";
            String[] usageLines = getUsageMessage();
            String errorMessage = e.getMessage();
            assertUsageLength(1);
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
        }
    }
}
