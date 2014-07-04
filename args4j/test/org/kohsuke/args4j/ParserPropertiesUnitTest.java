package org.kohsuke.args4j;

import junit.framework.TestCase;

public class ParserPropertiesUnitTest extends TestCase {
    public void testDefaults() {
        ParserProperties props = ParserProperties.defaults();
        assertEquals(80, props.getUsageWidth());
        assertEquals(ParserProperties.DEFAULT_COMPARATOR, props.getOptionSorter());
    }

    public void testSetToSame() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(80);
        assertEquals(80, props.getUsageWidth());
        assertEquals(ParserProperties.DEFAULT_COMPARATOR, props.getOptionSorter());
    }

    public void testSetToDifferent() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(90).withOptionSorter(null);
        assertEquals(90, props.getUsageWidth());
        assertEquals(null, props.getOptionSorter());
    }

    public void testSetOnlyOne() {
        ParserProperties props = ParserProperties.defaults().withOptionSorter(null);
        assertEquals(80, props.getUsageWidth());
        assertEquals(null, props.getOptionSorter());
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
}
