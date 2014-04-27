package org.kohsuke.args4j;

import java.io.StringWriter;

public class StringWithMetavarTest extends Args4JTestBase<StringWithMetavar> {
    @Override
    public StringWithMetavar getTestObject() {
        return new StringWithMetavar();
    }

    public void testSettingUsage() {
        args = new String[]{"-wrong-usage"};
        try {
            parser.parseArgument(args);
            fail("Doesn't detect wrong parameters.");
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

    private String getSingleLineUsage() {
        StringWriter buffer = new StringWriter();
        parser.printSingleLineUsage(buffer, null);
        buffer.flush();
        return buffer.toString();
    }

    public void testEqualsSeparator() {
        args = new String[]{"-wrong-usage"};
        parser.setUseEqualsForOptions(true);
        try {
            parser.parseArgument(args);
            fail("Doesn't detect wrong parameters.");
        } catch (CmdLineException e) {
            String expectedUsage   = " -str=METAVAR : set a string";
            String expectedSingleLineUsage   = " [-str=METAVAR]";
            String[] usageLines = getUsageMessage();
            String singleLineUsage = getSingleLineUsage();
            assertUsageLength(1);
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
            assertEquals("Got wrong usage summary", expectedSingleLineUsage, singleLineUsage);
        }
    }

    public void testExplicitNoEqualsSeparator() {
        args = new String[]{"-wrong-usage"};
        parser.setUseEqualsForOptions(false);
        try {
            parser.parseArgument(args);
            fail("Doesn't detect wrong parameters.");
        } catch (CmdLineException e) {
            String expectedUsage   = " -str METAVAR : set a string";
            String expectedSingleLineUsage   = " [-str METAVAR]";
            String[] usageLines = getUsageMessage();
            String singleLineUsage = getSingleLineUsage();
            assertUsageLength(1);
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
            assertEquals("Got wrong usage summary", expectedSingleLineUsage, singleLineUsage);
        }
    }
}
