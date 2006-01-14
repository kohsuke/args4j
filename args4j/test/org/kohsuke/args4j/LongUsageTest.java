package org.kohsuke.args4j;

public class LongUsageTest extends Args4JTest {
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
            //TODO test for implementation of parsing properties
            //String expectedLine3 = " -P<KEY>=<VALUE>                          : sets a key-value-pair";

            //TODO check the correct usage message - needs return value from parser
            /*
            String usageMessage = parser.printUsage(null).toString();
            String[] lines = usageMessage.split("\r\n");
            assertTrue("Wrong amount of lines in usage message", lines.length == 3);
            assertTrue("First line wrong", expectedLine1.equals(lines[0]));
            assertTrue("Second line wrong", expectedLine2.equals(lines[1]));
            assertTrue("Third line wrong", expectedLine3.equals(lines[2]));
            */
        }
    }

}