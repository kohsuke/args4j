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
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Restores the default Locale.
     */         
    @Override
    protected void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
    }

    public void testPrintExampleModeAll() {
        String s = new CmdLineParser(this).printExample(ExampleMode.ALL);
        assertEquals(" -a N -b <output> -c <ip address>", s);
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
}
