package org.kohsuke.args4j;

import org.kohsuke.args4j.EnumAttribute.Animal;

public class EnumAttributeTest extends Args4JTestBase<EnumAttribute> {

	@Override
	public EnumAttribute getTestObject() {
		return new EnumAttribute();
	}
	
	public void testSetEnum() throws CmdLineException {
		args = new String[]{"-animal", "HORSE"};
		parser.parseArgument(args);
		assertEquals(Animal.HORSE, testObject.myAnimal);
	}
	
	public void testSetEnumCaseInsensitive() throws CmdLineException {
		args = new String[]{"-animal", "horse"};
		parser.parseArgument(args);
		assertEquals(Animal.HORSE, testObject.myAnimal);
	}
	
	public void testIllegalEnum() {
		args = new String[]{"-animal", "ILLEGAL_ANIMAL"};
		try {
			parser.parseArgument(args);
			fail("Can't set ILLEGAL_ANIMAL as value.");
		} catch (CmdLineException e) {
			// exptected
		}
	}
	
	public void testUsage() {
		args = new String[]{"-wrong"};
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			assertUsageContains("Usage message should contain the enum VALUES", "HORSE");
			assertUsageContains("Usage message should contain the enum VALUES", "DUCK");
		}
	}

}
