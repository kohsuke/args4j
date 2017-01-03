package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import junit.framework.TestCase;

/** Simple test for the {@link NegatableBooleanOptionHandler}.
 * @author Robin Garner
 */
public class NegatableBooleanOptionHandlerTest extends TestCase {

    private static class SimpleTestBean {
	@Option(name = "--option", aliases = { "--nooption" },
		handler=NegatableBooleanOptionHandler.class,
		usage = "Standard option")
	private boolean option;
    }

    private static class NoNoTestBean {
	@Option(name = "--notify", aliases = { "--nonotify" },
		handler=NegatableBooleanOptionHandler.class,
		usage = "Option that naturally starts with --no")
	private boolean option = true;
    }


    public void testSimpleOptionPresent() throws Exception {
	SimpleTestBean args = new SimpleTestBean();
	CmdLineParser parser = new CmdLineParser(args);
	parser.parseArgument("--option");
	assertEquals(true, args.option);
    }

    public void testSimpleOptionNegated() throws Exception {
	SimpleTestBean args = new SimpleTestBean();
	CmdLineParser parser = new CmdLineParser(args);
	parser.parseArgument("--nooption");
	assertEquals(false, args.option);
    }

    public void testNoOptionPresent() throws Exception {
	NoNoTestBean args = new NoNoTestBean();
	CmdLineParser parser = new CmdLineParser(args);
	parser.parseArgument("--notify");
	assertEquals(true, args.option);
    }

    public void testNoOptionNegated() throws Exception {
	NoNoTestBean args = new NoNoTestBean();
	CmdLineParser parser = new CmdLineParser(args);
	parser.parseArgument("--nonotify");
	assertEquals(false, args.option);
    }
}
