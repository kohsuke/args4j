package org.kohsuke.args4j;


public class SimpleStringTest extends Args4JTestBase<SimpleString> {

    @Override
    public SimpleString getTestObject() {
        return new SimpleString();
    }

    public void testSettingStringNoValues() {
        SimpleString bo = testObject;
        args = new String[]{};
        try {
            parser.parseArgument(args);
            assertTrue("Default value set.", "default".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Call without parameters is valid!");
        }
    }

    public void testSettingString() {
        SimpleString bo = testObject;
        args = new String[]{"-str","test"};
        try {
            parser.parseArgument(args);
            assertTrue("Given value set.", "test".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Setting a string value should be possible");
        }
    }

    public void testSettingUsage() {
        args = new String[]{"-wrong-usage"};
        try {
            parser.parseArgument(args);
            fail("Doesnt detect wrong parameters.");
        } catch (CmdLineException e) {
            String expectedError = "\"-wrong-usage\" is not a valid option";
            String[] usageLines = getUsageMessage();
            assertErrorMessagePrefix(expectedError, e);
            assertUsageLength(2);
            assertEquals("Got wrong usage message", " -nu VAL     ", usageLines[0]);
            assertEquals("Got wrong usage message", " -str VAL : set a string", usageLines[1]);
        }
    }

    public void testMissingParameter() {
        args = new String[]{"-str"};
        try {
            parser.parseArgument(args);
            fail("Should miss one parameter.");
        } catch (CmdLineException e) {
            String expectedError = "Option \"-str\" takes an operand";
            String[] usageLines = getUsageMessage();
            String errorMessage = e.getMessage();
            assertUsageLength(2);
            assertTrue("Got wrong error message: " + errorMessage, errorMessage.startsWith(expectedError));
            assertEquals("Got wrong usage message", " -nu VAL     ", usageLines[0]);
            assertEquals("Got wrong usage message", " -str VAL : set a string", usageLines[1]);
        }
    }
    
    /*
     * Bug 5: Option without "usage" are hidden.
     * TODO: it seems that this is intended:
     *   http://weblogs.java.net/blog/kohsuke/archive/2005/05/parsing_command.html
     *   An @option without "usage" should not be displayed?
     *   If there is no usage information, the CmdLineParser.printOption()
     *   methods do explitely nothing. 
     */
    public void _testUsage() {
        args = new String[]{"-wrong"};
        try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			assertUsageContains("Usage does not contain -nu option", "-nu");
		}
    }

}
