package org.kohsuke.args4j;

import java.util.Map; 

public class PropsTest extends Args4JTestBase<Props> {

    @Override
    public Props getTestObject() {
        return new Props();
    }
    
    public void testDoNothing() {
        // ignore, that the other test cases are commented out
        // JUnit doesnt like TestCases without a test-method.
    }

    public void _testNoValues() {
        args = new String[]{};
        try {
            parser.parseArgument(args);
            Map map = testObject.props;
            assertTrue("Values illegally arrived.", map.size()==0);
        } catch (CmdLineException e) {
            fail("Call without parameters is valid!");
        }
    }
    
    public void _testSinglePair() {
        args = new String[]{"-Tkey1=value1"};
        try {
            parser.parseArgument(args);
            Map map = testObject.props;
            assertTrue("The key was not set.", map.containsKey("key1"));
            assertEquals("More keys than expected.", map.size(), 1);
            assertEquals("Key has wrong value", map.get("key1"), "value1");
        } catch (CmdLineException e) {
            fail("Cought an invalid exception");
        }
    }
    
    public void _testMultiplePairs() {
        args = new String[]{"-Tkey1=value1", "-Tkey2=value2", "-Tkey3=value3"};
        try {
            parser.parseArgument(args);
            Map map = testObject.props;
            assertTrue("A key was not set.", map.containsKey("key1"));
            assertTrue("A key was not set.", map.containsKey("key2"));
            assertTrue("A key was not set.", map.containsKey("key3"));
            assertEquals("Wrong number of keys.", map.size(), 3);
            assertEquals("Key has wrong value", map.get("key1"), "value1");
            assertEquals("Key has wrong value", map.get("key2"), "value2");
            assertEquals("Key has wrong value", map.get("key3"), "value3");
        } catch (CmdLineException e) {
            fail("Cought an invalid exception");
        }
    }

    public void _testDuplicateKey() {
        args = new String[]{"-Tkey1=value1", "-Tkey1=value1"};
        try {
            parser.parseArgument(args);
            Map map = testObject.props;
            assertTrue("A key was not set.", map.containsKey("key1"));
            assertEquals("Wrong number of keys.", map.size(), 1);
            // TODO: which value is set?
        } catch (CmdLineException e) {
            // TODO: or did an exception occur?
        }
    }
    
    public void _testInitialisation() {
        args = new String[]{"-Tkey1=value1"};
        try {
            testObject.props = null;
            parser.parseArgument(args);
            Map map = testObject.props;
            assertEquals("Key has wrong value", map.get("key1"), "value1");
        } catch (CmdLineException e) {
            // maybe a NPE?
        }
    }
    
}
