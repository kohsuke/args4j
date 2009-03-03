package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.StopOptionHandler;

import java.util.List;

public class MultivaluedTest extends Args4JTestBase<MultivaluedTest> {

    // The JavaBean part of this class as test object.
    
    // On Lists the @Option(multiValued) defaults to 'true'
    @Option(name="list")
    List<String> list;
    
    @Option(name="string", multiValued=true)
    String string;
    
    @Option(name="array", multiValued=true)
    String[] array;

    // The JUnit part of this class as tester.

    @Override
    public MultivaluedTest getTestObject() {
        return this;
    }

    public void testOnList() throws Exception {
        // The 'command line invocation'.
        parser.parseArgument("-list","one","two","three");
        // Check the results.
        assertEquals("Should got three values", 3, list.size());
        assertTrue(list.contains("one"));
        assertTrue(list.contains("two"));
        assertTrue(list.contains("three"));
    }
    
    //TODO: How to use 'multiValued' on plain fields?
    //      We have to option for Strings or CharacterSequence's to specify a separator
    //      and concating all values.
    //        @Option(... multiValued=true, seperator=",") String s; --> "one,two,three"
    //      As the use of 'separator' (new!) implies multiValued this would be more user friendly:
    //        @Option(... seperator=",") String s; --> "one,two,three"
    //      But how to handle other types like int,MyClass? Concatinating is not an option here...
    public void t_estOnString() throws Exception {
        // The 'command line invocation'.
        parser.parseArgument("-string","one","two","three");
    }

    //TODO: How to use 'multiValued' on arrays?
    //      There should be no difference to use on lists (from the user point of view).
    public void t_estOnArray() throws Exception {
        // The 'command line invocation'.
        parser.parseArgument("-array","one","two","three");
        // Check the results.
        assertEquals("Should got three values", 3, array.length);
    }
}
