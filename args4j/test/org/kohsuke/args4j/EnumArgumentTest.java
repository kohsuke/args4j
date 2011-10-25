package org.kohsuke.args4j;

import org.kohsuke.args4j.EnumAttribute.Animal;

import junit.framework.Assert;


public class EnumArgumentTest extends Args4JTestBase<EnumArgument> {

	@Override
	public EnumArgument getTestObject() {
		return new EnumArgument();
	}

	public void testSetEnum() throws CmdLineException {
		args = new String[]{"HORSE"};
		assertEquals(null, testObject.myAnimal);
		parser.parseArgument(args);
		assertEquals(Animal.HORSE, testObject.myAnimal);
	}

	public void testSetEnumCaseInsensitive() throws CmdLineException {
		args = new String[]{"horse"};
		assertEquals(null, testObject.myAnimal);
		parser.parseArgument(args);
		assertEquals(Animal.HORSE, testObject.myAnimal);
	}

	public void testIllegalArgumentOption() {
		String[] args = new String[] { "ILLEGAL_ANIMAL" };
		parseParameter(args, new EnumArgument());
	}

	private void parseParameter(String[] args, Object bean) {
		CmdLineParser parser = new CmdLineParser(bean);
		try {
			parser.parseArgument(args);
			Assert.fail();
		} catch (CmdLineException e) {
			if (args.length > 0) {
				Assert.assertTrue(
						"Illegal exception message: " + e.getMessage(),
						e.getMessage().startsWith(
								String.format("\"%s\" is not a valid value for \"", args[args.length - 1])
						));
			} else {
				Assert.assertEquals(
						"Illegal exception message: " + e.getMessage(),
						"Argument \"ANIMAL\" is required",
						e.getMessage());
			}
		}
	}

}
