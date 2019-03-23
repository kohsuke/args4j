package org.kohsuke.args4j;

import junit.framework.TestCase;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.ResourceBundle;

public class ArgumentTest extends TestCase {
    protected static class MultiValueHolder {
		@Argument
		public List<String> things;
	}

	protected static class SingleValueHolder {
		@Argument(metaVar = "thing", required = true)
		public String thing;
	}

	protected static class BooleanValueHolder {
		@Argument(metaVar = "thing", required = true)
		public boolean b;
	}

	protected static class I18NValueHolder {
		@Argument(usage = "FILE2READ", required = true)
		public File b;

		@Option(name = "-a", usage = "1 2 3")
		public String opt;
	}

	public void testMultiValue() throws Exception {
		MultiValueHolder holder = new MultiValueHolder();
		CmdLineParser parser = new CmdLineParser(holder);
		parser.parseArgument(new String[] { "one", "two" });

		assertEquals(2, holder.things.size());
		assertEquals("one", holder.things.get(0));
		assertEquals("two", holder.things.get(1));
	}

	public void testTooFew() throws Exception {
		SingleValueHolder holder = new SingleValueHolder();
		CmdLineParser parser = new CmdLineParser(holder);

		try {
			parser.parseArgument(new String[] {});
		} catch (CmdLineException e) {
			assertEquals("Argument \"thing\" is required", e.getMessage());
			return;
		}
		fail("expected " + CmdLineException.class);
	}

	public void testBoolean() throws Exception {
		BooleanValueHolder holder = new BooleanValueHolder();
		CmdLineParser parser = new CmdLineParser(holder);

		parser.parseArgument(new String[] { "true" });

		assertTrue(holder.b);
	}

	public void testIllegalBoolean() throws Exception {
		BooleanValueHolder holder = new BooleanValueHolder();
		CmdLineParser parser = new CmdLineParser(holder);

		try {
			parser.parseArgument(new String[] { "xyz" });
		} catch (CmdLineException e) {
			assertEquals("\"xyz\" is not a legal boolean value", e.getMessage());
			return;
		}
		fail("expected " + CmdLineException.class);
	}

	public void testI18N() {
		I18NValueHolder holder = new I18NValueHolder();
		CmdLineParser parser = new CmdLineParser(holder);
		StringWriter sw = new StringWriter();
		ResourceBundle rb = ResourceBundle.getBundle("org/kohsuke/args4j/ArgumentTestI18N");
		parser.printUsage(sw, rb);
		assertTrue(sw.toString()!=null);
	}
}
