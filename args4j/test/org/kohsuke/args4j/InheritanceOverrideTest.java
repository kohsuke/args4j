package org.kohsuke.args4j;

public class InheritanceOverrideTest extends Args4JTestBase<InheritanceOverride> {
    @Override
    public InheritanceOverride getTestObject() {
        return new InheritanceOverride();
    }

    @Override
    protected CmdLineParser createParser() {
        return new CmdLineParser(testObject, new ClassOverridableParser());
    }

    public void testMyselfRequired() {
        args = new String[]{"-f", "My father"};
        InheritanceOverride bo = testObject;
        try {
            parser.parseArgument(args);
            System.out.println("ParsedCorrectly");
            assertEquals("Value for class itself not arrived", "Thats me", bo.me);
        } catch (CmdLineException e) {
            System.out.println("ParseFailed");
            assertEquals("Option \"-m\" is required", e.getMessage());
        }
    }

    public void testMyself() {
        args = new String[]{"-m", "Thats me"};
        InheritanceOverride bo = testObject;
        try {
            parser.parseArgument(args);
            assertEquals("Value for class itself not arrived", "Thats me", bo.me);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }
}
