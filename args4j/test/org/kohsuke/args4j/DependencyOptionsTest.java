package org.kohsuke.args4j;

/**
 * @author Nicolas Geraud
 */
public class DependencyOptionsTest extends Args4JTestBase<DependencyOptions> {

    public void testEverybodyIsHere() throws Exception {
        args= new String[]{"-a","4" , "-b","3" , "-c","2" , "-d","1"};
        try {
            parser.parseArgument(args);
            assertEquals(testObject.w,4);
            assertEquals(testObject.x, 3);
            assertEquals(testObject.y,2);
            assertEquals(testObject.z,1);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    public void testEveryoneExceptRequires() throws Exception {
        args= new String[]{"-a","4" , "-b","3"};
        try {
            parser.parseArgument(args);
            assertEquals(testObject.w, 4);
            assertEquals(testObject.x,3);
            assertEquals(testObject.y,0);
            assertEquals(testObject.z,0);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    public void testSingleRequires() throws Exception {
        args= new String[]{"-a","4" , "-c","2"};
        try {
            parser.parseArgument(args);
            assertEquals(testObject.w,4);
            assertEquals(testObject.x, 0);
            assertEquals(testObject.y,2);
            assertEquals(testObject.z,0);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    public void testSingleRequiresFail() throws Exception {
        args= new String[]{"-c","2"};
        try {
            parser.parseArgument(args);
            fail();
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("requires the option(s) [--alpha]"));
        }
    }
    public void testMultipleRequiresFail1() throws Exception {
        args= new String[]{"--bravo","3" , "-d","1"};
        try {
            parser.parseArgument(args);
            fail();
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage(), e.getMessage().contains("requires the option(s) [-b, -c]"));
        }
    }
    public void testMultipleRequiresFail2() throws Exception {
        args= new String[]{"-a","4" , "-c","2" , "-d","1"};
        try {
            parser.parseArgument(args);
            fail();
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("requires the option(s) [-b, -c]"));
        }
    }

    public void testRecursive() throws Exception {
        args= new String[]{"-z","4" , "-y","3"};
        try {
            parser.parseArgument(args);
            assertEquals(testObject.a,4);
            assertEquals(testObject.b, 3);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    @Override
    public DependencyOptions getTestObject() {
        return new DependencyOptions();
    }
}
