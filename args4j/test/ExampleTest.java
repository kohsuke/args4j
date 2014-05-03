import java.io.File;
import java.net.InetAddress;
import java.util.Locale;

import junit.framework.TestCase;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;

/**
 * Tests {@link CmdLineParser#printExample(ExampleMode)}
 *
 * @author Kohsuke Kawaguchi
 */
public class ExampleTest extends TestCase {
    @Option(required = true, name = "-a", usage = "this is X")
    int x;

    @Option(name = "-b", usage = "this is Y", metaVar = "<output>")
    File y;

    @Option(name = "-c", usage = "this is Z")
    InetAddress z;

    private Locale defaultLocale;
    
    /**
     * Initializes the locale to english to fix error string comparing problems.
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        defaultLocale = Locale.getDefault();
    //    Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Restores the default Locale.
     */         
    @Override
    protected void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
    }

    @Option(name = "-h", usage = "this is H", forbids={"-b", "-c"})
    boolean h;
    
    @Option(name = "-h", usage = "this is H", forbids={"-b", "-c"})
    boolean h;
    
    public void testPrintExampleModeAll() {
        String s = new CmdLineParser(this).printExample(ExampleMode.ALL);
        assertEquals(" -a N -b <output> -c <ip address> -h", s);
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
}
