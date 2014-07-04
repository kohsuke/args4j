package org.kohsuke.args4j.spi;

import java.net.InetAddress;

import junit.framework.TestCase;

import org.kohsuke.args4j.CmdLineException;

public class InetAddressOptionHandlerTest extends TestCase {

	private InetAddressOptionHandler handler;

	@Override
	public void setUp() {
		handler = new InetAddressOptionHandler(null, null, null);
	}

	public void testParseSuccess() throws Exception {
		InetAddress expectedIp = InetAddress.getByAddress(new byte[] { (byte) 1,
				(byte) 2, (byte) 3, (byte) 4 });
		InetAddress ip = handler.parse("1.2.3.4");

		assertEquals(expectedIp, ip);
	}

	public void testParseFailure() throws Exception {
		try {
		handler.parse("bogus.ip.address.nosuch.");
		} catch (CmdLineException e) {
			assertEquals("\"bogus.ip.address.nosuch.\" must be an IP address", e.getMessage());
			return;
		}
		fail("We should not reach here");
	}
}
