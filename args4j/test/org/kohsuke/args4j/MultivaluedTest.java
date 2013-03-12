package org.kohsuke.args4j;

import java.util.List;

public class MultivaluedTest extends Args4JTestBase<MultivaluedTest> {

    // The JavaBean part of this class as test object.
    
    @Option(name="-list")
    List<String> list;
    
    @Option(name="-string")
    String string;
    
    @Option(name="-array")
    String[] array;

    // The JUnit part of this class as tester.

    @Override
    public MultivaluedTest getTestObject() {
        return this;
    }

    public void testOnList() throws Exception {
        // The 'command line invocation'.
        setArgs(
            "-list", "one",
            "-list", "two",
            "-list", "three"
        );
        parser.parseArgument(args);
        // Check the results.
        assertEquals("Should got three values", 3, list.size());
        assertTrue(list.contains("one"));
        assertTrue(list.contains("two"));
        assertTrue(list.contains("three"));
    }

    /**
     * Specifying an option multiple times can get no-op, such as in the case when the field can only retain one value.
     */
    public void testOnString() throws Exception {
        // The 'command line invocation'.
        parser.parseArgument("-string","one","-string","two","-string","three");
        assertEquals("three",string);
    }

    public void testOnArray() throws Exception {
        // The 'command line invocation'.
        parser.parseArgument("-array","one","-array","two","-array","three");
        // Check the results.
        assertEquals("Should got three values", 3, array.length);
        assertEquals("one",array[0]);
        assertEquals("two",array[1]);
        assertEquals("three",array[2]);
    }
}
