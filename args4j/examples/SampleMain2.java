import java.io.PrintWriter;

import org.apache.tools.ant.BuildException;
import org.kohsuke.args4j.Args4jTask;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.MissingRequiredArgumentException;
import org.kohsuke.args4j.opts.BooleanCmdLineItem;
import org.kohsuke.args4j.opts.IntCmdLineItem;
import org.kohsuke.args4j.opts.OutputStreamCmdLineItem;
import org.kohsuke.args4j.opts.StringCmdLineItem;
import org.kohsuke.args4j.opts.StringsCmdLineItem;


/**
 * Another sample program that shows how you can use args4j.
 * 
 * <p>
 * This example shows how your program can support both
 * the standard command line interface and the ant task
 * in one class.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class SampleMain2 extends Args4jTask {

    public BooleanCmdLineItem recursive = new BooleanCmdLineItem("-r","recursively process something");
    public OutputStreamCmdLineItem out  = new OutputStreamCmdLineItem("-o","set the output file name","-");
    // you can also specify the default value
    public StringCmdLineItem str        = new StringCmdLineItem("-str","a random string option","(default value)");
    public IntCmdLineItem num           = new IntCmdLineItem("-n","security alert level");
    
    public StringsCmdLineItem sargs   = new StringsCmdLineItem("-args","additional options to javac");
    
    
    public static void main(String[] args) {
        SampleMain2 main = new SampleMain2();
        try {
            // just call the doMain method. the arguments will
            // be parsed and the doExecute method will be called.
            main.doMain(args);
        } catch (CmdLineException e) {
            // if there's any command-line error.
            // this will report an error message.
            System.err.println(e.getMessage());
            // this is a good place to print out the usage screen.
            main.getParser().printUsage("java SampleMain2",new PrintWriter(System.err));
        }
    }
    
    /**
     * Creates a {@link CmdLineParser}.
     * 
     * @see org.kohsuke.args4j.Args4jTask#createCmdLineParser()
     */
    protected CmdLineParser createCmdLineParser() throws BuildException {
        CmdLineParser parser = new CmdLineParser();
        // this will add all the option fields in this object
        parser.addOptionClass(this);
        // but you still need to add arguments manually,
        // because order of arguments matter.
        parser.addArgument(sargs);
        return parser;
    }

    /**
     * @see org.kohsuke.args4j.Args4jTask#doExecute()
     */
    protected void doExecute() throws BuildException,CmdLineException {
        // before running, you should check
        // if enough arguments are given.
        if( sargs.values.isEmpty() )
            throw new MissingRequiredArgumentException();
        
        // do the real work
        ;
    }

}
