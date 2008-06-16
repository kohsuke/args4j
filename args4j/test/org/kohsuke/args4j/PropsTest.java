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

    public void testNoValues() {
        args = new String[]{};
        try {
            parser.parseArgument(args);
            Map<String,String> map = testObject.props;
            if (map == null) {
            	// as expected
            	return;
            }
            assertTrue("Values illegally arrived.", map.size()==0);
        } catch (CmdLineException e) {
            fail("Call without parameters is valid! " + e.getMessage());
        }
    }
    
    public void testSinglePair() {
        args = new String[]{"-T", "key1=value1"};
        try {
            parser.parseArgument(args);
            Map<String,String> map = testObject.props;
            assertTrue("The key was not set.", map.containsKey("key1"));
            assertEquals("More keys than expected.", map.size(), 1);
            assertEquals("Key has wrong value", map.get("key1"), "value1");
        } catch (CmdLineException e) {
            fail("Cought an invalid exception: " + e.getMessage());
        }
    }
    
    public void testMultiplePairs() {
        args = new String[]{"-T", "key1=value1", "-T", "key2=value2", "-T", "key3=value3"};
        try {
            parser.parseArgument(args);
            Map<String,String> map = testObject.props;
            assertTrue("A key was not set.", map.containsKey("key1"));
            assertTrue("A key was not set.", map.containsKey("key2"));
            assertTrue("A key was not set.", map.containsKey("key3"));
            assertEquals("Wrong number of keys.", map.size(), 3);
            assertEquals("Key has wrong value", map.get("key1"), "value1");
            assertEquals("Key has wrong value", map.get("key2"), "value2");
            assertEquals("Key has wrong value", map.get("key3"), "value3");
        } catch (CmdLineException e) {
            fail("Cought an invalid exception: " + e.getMessage());
        }
    }

    public void testDuplicateKey() {
        args = new String[]{"-T", "key1=one", "-T", "key1=two"};
        try {
            parser.parseArgument(args);
            Map<String,String> map = testObject.props;
            assertTrue("A key was not set.", map.containsKey("key1"));
            assertEquals("Wrong number of keys.", map.size(), 1);
            assertEquals("As Map.put() defines: last put wins.", map.get("key1"), "two"); 
        } catch (CmdLineException e) {
            fail("Cought an invalid exception: " + e.getMessage());
        }
    }
    
    public void testInitialisation() {
        args = new String[]{"-T", "key1=value1"};
        try {
            testObject.props = null;
            parser.parseArgument(args);
            Map<String,String> map = testObject.props;
            assertNotNull("Map should have been initialized with a new HashMap", map);
            assertEquals("Key has wrong value", map.get("key1"), "value1");
        } catch (CmdLineException e) {
            fail("Cought an invalid exception: " + e.getMessage());
        }
    }
    
    public void testNoValue() {
        args = new String[]{"-T", "key="};
        try {
            parser.parseArgument(args);
            Map<String,String> map = testObject.props;
            assertNull("Key has wrong value", map.get("key"));
        } catch (CmdLineException e) {
            fail("Cought an invalid exception: " + e.getMessage());
        }
    }
    
    public void testNoKey() {
        args = new String[]{"-T", "=value"};
        try {
            parser.parseArgument(args);
            fail("An exception should have been thrown.");
        } catch (CmdLineException e) {
        	assertEquals("Wrong error message.", "A key must be set.", e.getMessage());
        }
    }
    
    public void testNoSplitCharacter() {
        args = new String[]{"-T", "keyvalue"};
        try {
            parser.parseArgument(args);
            fail("An exception should have been thrown.");
        } catch (CmdLineException e) {
        	assertEquals("Wrong error message.", "An argument for setting a Map must contain a '='", e.getMessage());
        }
    }
    
}
