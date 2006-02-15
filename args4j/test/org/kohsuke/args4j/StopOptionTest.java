package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.StopOptionHandler;

import java.util.List;

/**
 * Tests {@link StopOptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class StopOptionTest extends Args4JTestBase<StopOptionTest> {

    @Argument
    @Option(name="--",handler= StopOptionHandler.class)
    List<String> args;

    @Option(name="-n")
    int n;

    @Override
    public StopOptionTest getTestObject() {
        return this;
    }

    public void test1() throws Exception {
        parser.parseArgument("-n","5","abc","def");

        assertEquals(5,n);
        assertEquals(2,args.size());
        assertEquals("abc",args.get(0));
        assertEquals("def",args.get(1));
    }

    public void test2() throws Exception {
        parser.parseArgument("--","-n","5","abc","def");

        assertEquals(0,n);
        assertEquals(4,args.size());
        assertEquals("-n",args.get(0));
        assertEquals("5",args.get(1));
    }
}
