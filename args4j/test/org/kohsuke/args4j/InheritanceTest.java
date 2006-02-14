package org.kohsuke.args4j;

public class InheritanceTest extends Args4JTestBase {

    @Override
    public Object getTestObject() {
        return new Inheritance();
    }
    
    public void testMyself() {
        args = new String[]{"-m","Thats me"};
        Inheritance bo = (Inheritance)testObject;
        try {
            parser.parseArgument(args);
            assertEquals("Value for class itself not arrived", "Thats me", bo.me);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }

    public void testFather() {
        args = new String[]{"-f","My father"};
        Inheritance bo = (Inheritance)testObject;
        try {
            parser.parseArgument(args);
            assertEquals("Value for class itself not arrived", "My father", bo.father);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }

    public void testGrandfather() {
        args = new String[]{"-g","My fathers father"};
        Inheritance bo = (Inheritance)testObject;
        try {
            parser.parseArgument(args);
            assertEquals("Value for class itself not arrived", "My fathers father", bo.grandpa);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }
    
    public void testMother() {
        args = new String[]{"-mom","Hi Mom"};
        Inheritance bo = (Inheritance)testObject;
        try {
            parser.parseArgument(args);
            assertNull("Annotations are not designed for use in interfaces", bo.mom);
        } catch (CmdLineException e) {
            //no-op
        }
    }
    
}
