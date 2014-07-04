import junit.framework.TestCase;
import org.kohsuke.args4j.*;

import java.io.File;
import java.net.InetAddress;
import java.util.Locale;

/**
 * Tests {@link CmdLineParser#printExample(ExampleMode)}
 *
 * @author Kohsuke Kawaguchi
 */
public class ExampleTest extends TestCase {
    // DOr sort test, start with these not in alphabetical order

    @Option(name = "-h", usage = "this is H", forbids={"-b", "-c"})
    boolean h;

    @Option(required = true, name = "-a", usage = "this is X")
    int x;

    @Option(name = "-b", usage = "this is Y", metaVar = "<output>")
    File y;

    @Option(name = "-c", usage = "this is Z")
    InetAddress z;

    private Locale oldDefault;
    
    @Override
    public void setUp() {
        oldDefault = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
    }

    @Override
    protected void tearDown() throws Exception {
        Locale.setDefault(oldDefault);
    }    
    
    public void testPrintExampleModeAll() {
        String s = new CmdLineParser(this).printExample(ExampleMode.ALL);
        assertEquals(" -a N -b <output> -c IP ADDRESS -h", s);
    }

    public void testPrintExampleModeRequired() {
        String s = new CmdLineParser(this).printExample(ExampleMode.REQUIRED);
        assertEquals(" -a N", s);
    }

    public void testParsingAllArgs() throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument("-a", "1", "-b", "foo", "-c", "1.2.3.4");
        assertEquals(1, x);
        assertEquals(new File("foo"), y);
        assertEquals(InetAddress.getByName("1.2.3.4"), z);
    }

    public void testParsingOnlyRequiredArgs() throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument("-a", "1");
        assertEquals(1, x);
        assertNull(y);
        assertNull(z);
    }

    public void testParsingMissingRequiredArgs() throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument("-b", "foo");
            fail("Should have thrown an exception because"
                    + " required arg 'a' is missing");
        } catch (CmdLineException e) {
            assertEquals("Option \"-a\" is required", e.getMessage());
        }
    }
    
    public void testForbidArgs() throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        try {
        	parser.parseArgument("-h", "-a", "2", "-b", "1");
        	fail("Should have thrown an exception because"
                    + " arg 'h' forbids 'b'");
        } catch (CmdLineException e) {
            assertEquals("option \"-h\" cannot be used with the option(s) [-b, -c]", e.getMessage());
        }
    }

    public void testNoOptionsSort() {
        ParserProperties properties = ParserProperties.defaults().optionSorter(null);
        String s = new CmdLineParser(this, properties).printExample(ExampleMode.ALL);
        assertEquals(" -h -a N -b <output> -c IP ADDRESS", s);
    }
}
