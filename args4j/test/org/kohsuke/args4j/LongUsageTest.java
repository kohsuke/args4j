package org.kohsuke.args4j;

public class LongUsageTest extends Args4JTestBase<LongUsage> {
    @Override
    public LongUsage getTestObject() {
        return new LongUsage();
    }

    public void testUsageMessage() {
        args = new String[]{"-wrong-usage"};
        try {
        	// set Widescreen otherwise a line wrapping must occur
        	parser.setUsageWidth(120);
        	// start parsing
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            String expectedLine1 = " -LongNamedStringOption USE_A_NICE_STRING : set a string";
            String expectedLine2 = " -i N                                     : set an int";
            String[] usageLines = getUsageMessage();
            assertUsageLength(2);
            assertEquals("First line wrong", expectedLine1, usageLines[0]);
            assertEquals("Second line wrong", expectedLine2, usageLines[1]);
        }
    }

    public void testUsageMessageWithNewWayToSet() {
        args = new String[]{"-wrong-usage"};
        // set Widescreen otherwise a line wrapping must occur
        parser = new CmdLineParser(testObject,
                ParserProperties.defaults().withUsageWidth(120));
        try {
        	// start parsing
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            String expectedLine1 = " -LongNamedStringOption USE_A_NICE_STRING : set a string";
            String expectedLine2 = " -i N                                     : set an int";
            String[] usageLines = getUsageMessage();
            assertUsageLength(2);
            assertEquals("First line wrong", expectedLine1, usageLines[0]);
            assertEquals("Second line wrong", expectedLine2, usageLines[1]);
        }
    }

}
