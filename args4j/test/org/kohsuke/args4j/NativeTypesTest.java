package org.kohsuke.args4j;

public class NativeTypesTest extends Args4JTestBase<NativeTypes> {

	@Override
	public NativeTypes getTestObject() {
		return new NativeTypes();
	}
	
	public void testBooleanTrue() throws CmdLineException  {
		args = new String[]{"-boolean"};
		parser.parseArgument(args);
		assertTrue(testObject._boolean);
	}
	
	public void testBooleanFalse() throws CmdLineException {
		args = new String[]{};
		parser.parseArgument(args);
		assertFalse(testObject._boolean);
	}
	
	public void testByte() throws CmdLineException {
		args = new String[]{"-byte", "42"};
		parser.parseArgument(args);
		assertEquals(42, testObject._byte);
	}
	
	public void testChar() throws CmdLineException {
		args = new String[]{"-char", "a"};
		parser.parseArgument(args);
		assertEquals('a', testObject._char);
	}
	
	public void testDouble() throws CmdLineException {
		args = new String[]{"-double", "42"};
		parser.parseArgument(args);
		assertEquals(42, testObject._double, 0);
	}
	
	public void testFloat() throws CmdLineException {
		args = new String[]{"-float", "42"};
		parser.parseArgument(args);
		assertEquals(42, testObject._float, 0);
	}
	
	public void testInt() throws CmdLineException {
		args = new String[]{"-int", "42"};
		parser.parseArgument(args);
		assertEquals(42, testObject._int);
	}
	
	public void testLong() throws CmdLineException {
		args = new String[]{"-long", "42"};
		parser.parseArgument(args);
		assertEquals(42, testObject._long);
	}
	
	public void testShort() throws CmdLineException {
		args = new String[]{"-short", "42"};
		parser.parseArgument(args);
		assertEquals(42, testObject._short);
	}
}
