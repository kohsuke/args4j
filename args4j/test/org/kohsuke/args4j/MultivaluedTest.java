package org.kohsuke.args4j;

import java.util.List;

public class MultivaluedTest extends Args4JTestBase<MultivaluedTest> {

    // The JavaBean part of this class as test object.
    
    @Option(name="-list")
    List<String> list;
    
    @Option(name="-string")
    String string;
    
    // There is no OptionHandler for Arrays 
    //@Option(name="-array", multiValued=true)
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

    //TODO: How to use 'multiValued' on arrays?
    //      There should be no difference to use on lists (from the user point of view).
    public void t_estOnArray() throws Exception {
        // The 'command line invocation'.
        parser.parseArgument("-array","one","-array","two","-array","three");
        // Check the results.
        assertEquals("Should got three values", 3, array.length);
    }
}
