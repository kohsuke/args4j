package org.kohsuke.args4j;

import junit.framework.TestCase;

public class ParserPropertiesUnitTest extends TestCase {
    public void testDefaults() {
        ParserProperties props = ParserProperties.defaults();
        assertEquals(80, props.getUsageWidth());
        assertEquals(true, props.shouldSortOptions());
    }

    public void testSetToSame() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(80).shouldSortOptions(true);
        assertEquals(80, props.getUsageWidth());
        assertEquals(true, props.shouldSortOptions());
    }

    public void testSetToDifferent() {
        ParserProperties props = ParserProperties.defaults().withUsageWidth(90).shouldSortOptions(false);
        assertEquals(90, props.getUsageWidth());
        assertEquals(false, props.shouldSortOptions());
    }

    public void testSetOnlyOne() {
        ParserProperties props = ParserProperties.defaults().shouldSortOptions(false);
        assertEquals(80, props.getUsageWidth());
        assertEquals(false, props.shouldSortOptions());
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
