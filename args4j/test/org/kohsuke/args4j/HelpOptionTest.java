package org.kohsuke.args4j;

/** 
 * JUnit test for checking whether {@link Option#help() works}.
 * @author Stephan Fuhrmann
 */
public class HelpOptionTest extends Args4JTestBase<HelpOption> {
    @Override
    public HelpOption getTestObject() {
        return new HelpOption();
    }

    public void testWithRequiredOptionMissing() {
        args = new String[]{"-opt", "operand"};
        try {
            parser.parseArgument(args);
            fail("Doesnt detect missing parameters.");
        } catch (CmdLineException e) {
            String expectedError = "Option \"-req\" is required";
            String[] usageLines = getUsageMessage();
            String errorMessage = e.getMessage();
            assertUsageLength(3);
            assertTrue("Got wrong error message", errorMessage.startsWith(expectedError));
        }
    }
    
    public void testWithRequiredOptionExisting() throws CmdLineException {
        args = new String[]{"-req", "operand"};
        parser.parseArgument(args);

        assertFalse(testObject.help);
        assertNull(testObject.optional1);
        assertEquals("operand", testObject.req1);
    }
    
    public void testWithHelpExistingButRequiredOptionMissing() throws CmdLineException {
        args = new String[]{"-help"};
        parser.parseArgument(args);

        assertTrue(testObject.help);
        assertNull(testObject.optional1);
        assertNull(testObject.req1);
    }
}
