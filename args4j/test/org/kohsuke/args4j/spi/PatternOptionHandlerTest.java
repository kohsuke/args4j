package org.kohsuke.args4j.spi;

import java.util.Locale;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import org.kohsuke.args4j.CmdLineException;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/** Simple test for the {@link PatternOptionHandler}.
 * @author Stephan Fuhrmann
 */
public class PatternOptionHandlerTest extends TestCase {

    private class TestBean {
        @Option(name="-pattern")
        private Pattern pattern;
    }
    
	public void testParseSuccess() throws Exception {
        
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-pattern", ".*");
        
		assertEquals(Pattern.compile(".*").toString(), bean.pattern.toString());
	}
    
    
	public void testParseFail() throws Exception {
        
        TestBean bean = new TestBean();
        // enforce english for string comparision below
        Locale.setDefault(Locale.ENGLISH);
        CmdLineParser parser = new CmdLineParser(bean);
        try {
            parser.parseArgument("-pattern", "*");
            fail("Expecting exception");
        }
        catch (CmdLineException e) {
            assertEquals("\"-pattern\" must be a regular expression", e.getMessage());
        }
	}

}
