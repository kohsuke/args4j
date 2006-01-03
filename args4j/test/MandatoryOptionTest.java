import junit.framework.TestCase;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Tests mandatory options.
 *
 * @author Kohsuke Kawaguchi
 */
public class MandatoryOptionTest extends TestCase {
    @Option(required=true,name="-a")
    int x;

    @Option(name="-b")
    int y;

    public void test1() throws Exception {
        CmdLineParser p = new CmdLineParser(this);
        p.parseArgument("-a","3","-b","2");
        assertEquals(x,3);
        assertEquals(y,2);

        p = new CmdLineParser(this);
        try {
            p.parseArgument("-b","2");
            fail();
        } catch(CmdLineException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("-a"));
        }
    }
}
