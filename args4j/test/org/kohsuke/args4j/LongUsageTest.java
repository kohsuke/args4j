package org.kohsuke.args4j;

public class LongUsageTest extends Args4JTestBase {
    @Override
    public Object getTestObject() {
        return new LongUsage();
    }

    public void testUsageMessage() {
        args = new String[]{"-wrong-usage"};
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            String expectedLine1 = " -LongNamedStringOption USE_A_NICE_STRING : set a string";
            String expectedLine2 = " -i N                                     : set an int";
            String[] usageLines = getUsageMessage();
            assertUsageLength(2);
            assertTrue("First line wrong", expectedLine1.equals(usageLines[0]));
            assertTrue("Second line wrong", expectedLine2.equals(usageLines[1]));
        }
    }
   
}