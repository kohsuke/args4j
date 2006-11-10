import junit.framework.TestCase;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * Tests {@link CmdLineParser#printExample(ExampleMode)}
 *
 * @author Kohsuke Kawaguchi
 */
public class ExampleTest extends TestCase {
    @Option(required=true,name="-a",usage="this is X")
    int x;

    @Option(name="-b",usage="this is Y",metaVar="<output>")
    File y;

    public void test1() {
        String s = new CmdLineParser(this).printExample(ExampleMode.ALL);
        assertEquals(" -a N -b <output>",s);
    }

    public void test2() {
        String s = new CmdLineParser(this).printExample(ExampleMode.REQUIRED);
        assertEquals(" -a N",s);
    }
}
