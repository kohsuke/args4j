package org.kohsuke.args4j.spi;

import java.util.Arrays;
import java.util.Locale;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import junit.framework.TestCase;
import org.kohsuke.args4j.CmdLineException;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Simple test for the {@link MacAddressOptionHandler}.
 *
 * @author Stephan Fuhrmann
 */
public class MacAddressOptionHandlerTest extends TestCase {

    private class TestBean {

        @Option(name = "-mac", handler = MacAddressOptionHandler.class)
        private byte addr[];
    }

    public void testParseSuccessWithoutSixPartFormatUnsigned() throws Exception {
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-mac", "00:11:22:33:44:55");

        assertEquals(Arrays.toString(new byte[]{0x00, 0x11, 0x22, 0x33, 0x44, 0x55}), Arrays.toString(bean.addr));
    }
    
    public void testParseSuccessWithSixPartFormatSign() throws Exception {
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-mac", "80:f1:72:33:c4:d5");

        assertEquals(Arrays.toString(new byte[]{(byte)0x80, (byte)0xf1, 0x72, 0x33, (byte)0xc4, (byte)0xd5}), Arrays.toString(bean.addr));
    }
    
    public void testParseSuccessWithContinuousFormat() throws Exception {
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-mac", "80f17233c4d5");

        assertEquals(Arrays.toString(new byte[]{(byte)0x80, (byte)0xf1, 0x72, 0x33, (byte)0xc4, (byte)0xd5}), Arrays.toString(bean.addr));
    }
    
    public void testParseSuccessWithSpacedFormat() throws Exception {
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-mac", "80 f1 72 33 c4 d5");

        assertEquals(Arrays.toString(new byte[]{(byte)0x80, (byte)0xf1, 0x72, 0x33, (byte)0xc4, (byte)0xd5}), Arrays.toString(bean.addr));
    }
    
    public void testParseSuccessWithMinusFormat() throws Exception {
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-mac", "80-f1-72-33-c4-d5");

        assertEquals(Arrays.toString(new byte[]{(byte)0x80, (byte)0xf1, 0x72, 0x33, (byte)0xc4, (byte)0xd5}), Arrays.toString(bean.addr));
    }

    public void testParseFail() throws Exception {

        Locale old = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        try {
            parser.parseArgument("-mac", "00:11:22:33:44:55:ff");
            fail("Expecting exception");
        } catch (CmdLineException e) {
            assertEquals("\"00:11:22:33:44:55:ff\" must be an MAC address", e.getMessage());
        } finally {
            Locale.setDefault(old);
        }
    }
}
