package org.kohsuke.args4j.spi;

import junit.framework.TestCase;
import org.kohsuke.args4j.CmdLineException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: kmahoney
 * Date: 6/7/13
 * Time: 6:09 AM
 */

public class PathOptionHandlerTest extends TestCase {
    static final String TEST_1 = "/path/test";
    static final String TEST_2 = "bad/path/\0";

    private PathOptionHandler handler;

    @Override
    public void setUp() {
        handler = new PathOptionHandler(null, null, null);
    }

    public void testParseSuccess() throws Exception {
        Path expectedIp = Paths.get(TEST_1);
        Path ip = handler.parse(TEST_1);

        assertEquals(expectedIp, ip);
    }

    public void testParseFailure() throws Exception {
        try {
            handler.parse(TEST_2);
        } catch (CmdLineException e) {
            //assertEquals("\"a.b.c.d\" must be an IP address", e.getMessage());
            return;
        }
        fail("Invalid Path Should Have Thrown Exception");
    }

    public void testNullParseFailure() throws Exception {
        try {
            handler.parse(null);
        } catch (CmdLineException e) {
            //assertEquals("\"a.b.c.d\" must be an IP address", e.getMessage());
            return;
        }
        fail("Null Path Should Have Thrown Exception");
    }
}
