package org.kohsuke.args4j;

import junit.framework.TestCase;

public class ParserPropertiesUnitTest extends TestCase {
    public void testDefaults() {
        ParserProperties props = ParserProperties.defaults();
        assertEquals(80, props.getUsageWidth());
        assertEquals(true, props.willSortOptions());
    }

    public void testSetToSame() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(80).doSortOptions(true);
        assertEquals(80, props.getUsageWidth());
        assertEquals(true, props.willSortOptions());
    }

    public void testSetToDifferent() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(90).doSortOptions(false);
        assertEquals(90, props.getUsageWidth());
        assertEquals(false, props.willSortOptions());
    }

    public void testSetOnlyOne() {
        ParserProperties props = ParserProperties.defaults().doSortOptions(false);
        assertEquals(80, props.getUsageWidth());
        assertEquals(false, props.willSortOptions());
    }

    public void testFailOnNegativeWidth() {
        try {
            ParserProperties.defaults().withUsageWidth(-1);
            fail("accepted negative width");
        } catch (IllegalArgumentException x) {
            // OK
        }
    }

    public void testAcceptPositiveWidth() {
        try {
            ParserProperties.defaults().withUsageWidth(0);
        } catch (IllegalArgumentException x) {
            fail("rejected zero width");
        }
    }

    public void testEachIsImmutable() {
        ParserProperties firstProps = ParserProperties.defaults();
        ParserProperties secondProps = firstProps.doSortOptions(false);
        assertEquals("changing property mutated original", true, firstProps.willSortOptions());
        assertEquals(false, secondProps.willSortOptions());
    }
}
