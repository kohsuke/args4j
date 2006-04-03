package org.kohsuke.args4j;

import junit.framework.TestCase;

public class PrintUsageTest extends Args4JTestBase {

	private class Bean {
		@Option(name="s",usage="1234567890123456789012345678901234567890")
		public String s;
	}

	@Override
	public Object getTestObject() {
		return new Bean();
	}
	
	
	
	public void testEnoughLength() {
		String[] args = {"-wrong"};
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			String[] usageMessage = getUsageMessage();
			assertEquals("Shouldn't split the lines.", 1, usageMessage.length);
		}
	}

	public void testTooSmallLength() {
		String[] args = {"-wrong"};
		try {
			parser.setUsageWidth(30);
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			String[] usageMessage = getUsageMessage();
			assertEquals("Should split the lines.", 2, usageMessage.length);
		}
	}
	
	
}
