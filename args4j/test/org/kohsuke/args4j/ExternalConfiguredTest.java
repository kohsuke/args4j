package org.kohsuke.args4j;

import org.kohsuke.args4j.XmlParser;
import org.xml.sax.InputSource;

import java.io.File;
import java.util.List;

public class ExternalConfiguredTest extends Args4JTestBase<ExternalConfiguredTest> {

	boolean recursive = false;
    File out;
    String str = "default";
    public void setStr(String s) {
        str = s.toUpperCase();
    }
    boolean data = false;
    List<String> arguments;

    @Override
    public ExternalConfiguredTest getTestObject() {
        return this;
    }

    @Override
    protected CmdLineParser createParser() {
        CmdLineParser p = new CmdLineParser(new Object());
        new XmlParser().parse(getClass().getResource("ExternalConfiguredTest.xml"),p,this);
        return p;
    }

    public void testNoArgsGiven() throws CmdLineException {
		args = new String[] {};
		parser.parseArgument(args);
		assertTrue("Default value for 'str'.", "default".equals(str));
		assertFalse("Default for 'recursive'.", recursive);
		assertFalse("Default for 'data'.", data);
	}

    public void testFieldSetter() throws CmdLineException {
		args = new String[] { "-o", "myfile.txt" };
		parser.parseArgument(args);
		assertTrue("Default value for 'str'.", "default".equals(str));
		assertFalse("Default for 'recursive'.", recursive);
		assertFalse("Default for 'data'.", data);
		assertEquals("myfile.txt", out.getName());
    }

	public void testMethodSetter() throws CmdLineException {
		args = new String[] { "-str", "myvalue" };
		parser.parseArgument(args);
		assertFalse("Default for 'recursive'.", recursive);
		assertFalse("Default for 'data'.", data);
		assertTrue("Method setter for '-str' doesnt work.", "MYVALUE".equals(str));
	}

	public void testCustomHandler() throws CmdLineException {
		args = new String[] { "-custom" };
		parser.parseArgument(args);
		assertTrue("Default value set.", "default".equals(str));
		assertFalse("Default for 'recursive'.", recursive);
		assertTrue("Value per custom handler on 'data' not set.", data);
	}

	public void testUsage() {
		assertUsageContains("'usage' is not evaluated", "recursively run something");
	}
}