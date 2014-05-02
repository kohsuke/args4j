package org.kohsuke.args4j.spi;

import java.util.Arrays;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.kohsuke.args4j.Argument;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Test for {@link StringArrayOptionHandler}.
 * 
 * The failure of the tests with defaults are IMHO a design problem
 * in {@link ArrayFieldSetter}. My expectation is that the default
 * values get overwritten as soon as one command line option is given.
 * But the command line options are always added to the list of defaults which
 * is a little bit confusing.
 * 
 * @author Stephan Fuhrmann
 */
public class StringArrayOptionHandlerTest extends TestCase {

    private static class TestBean {

        @Option(name = "-opt")
        private String stringArray[] = new String[]{"def1", "def2", "def3"};
        
        @Argument
        private String rest[];
    }

    public void testParseWithDefault() throws Exception {

        TestBean bean = new TestBean();

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "test3");

        Assert.assertEquals(Arrays.asList("def1", "def2", "def3"), Arrays.asList(bean.stringArray));
        Assert.assertEquals(Arrays.asList("test1", "test2", "test3"), Arrays.asList(bean.rest));
    }

    public void testParseWithOneParam() throws Exception {

        TestBean bean = new TestBean();

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "-opt", "test3");

        Assert.assertEquals(Arrays.asList("test3"), Arrays.asList(bean.stringArray));
        Assert.assertEquals(Arrays.asList("test1", "test2"), Arrays.asList(bean.rest));
    }
    
    public void testParseWithTwoParams() throws Exception {

        TestBean bean = new TestBean();

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "-opt", "test3", "-opt", "test4");

        Assert.assertEquals(Arrays.asList("test3", "test4"), Arrays.asList(bean.stringArray));
        Assert.assertEquals(Arrays.asList("test1", "test2"), Arrays.asList(bean.rest));
    }
    
   public void testParseWithNoDefault() throws Exception {

        TestBean bean = new TestBean();
        bean.stringArray = null; // remove
        
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "-opt", "test3", "-opt", "test4");

        Assert.assertEquals(Arrays.asList("test3", "test4"), Arrays.asList(bean.stringArray));
        Assert.assertEquals(Arrays.asList("test1", "test2"), Arrays.asList(bean.rest));
    }    
}
