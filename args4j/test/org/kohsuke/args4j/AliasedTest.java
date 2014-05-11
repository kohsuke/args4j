package org.kohsuke.args4j;

public class AliasedTest extends Args4JTestBase<Aliased> {
    @Override
    public Aliased getTestObject() {
        return new Aliased();
    }

    public void testMissingParameter() {
        setArgs("-str");
        try {
            parser.parseArgument(args);
            fail("Should miss one parameter.");
        } catch (CmdLineException e) {
            String expectedError = "Option \"-str (--long-str)\" takes an operand";
            String expectedUsage   = " -str (--long-str) METAVAR : set a string";
            String[] usageLines = getUsageMessage();
            assertUsageLength(1);
            assertErrorMessagePrefix(expectedError, e);
            assertEquals("Got wrong usage message", expectedUsage, usageLines[0]);
        }
    }

    public void testAlias() throws Exception {
        setArgs("--long-str", "something");
        parser.parseArgument(args);
        assertEquals("something", testObject.str);
    }
}
