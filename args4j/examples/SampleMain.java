import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Sample program that shows how you can use args4j.
 *
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class SampleMain {

    @Option(name="-r",usage="recursively run something")
    private boolean recursive;

    @Option(name="-o",usage="output to this file",metaVar="OUTPUT")
    private File out = new File(".");

    @Option(name="-str")        // no usage
    private String str = "(default value)";

    @Option(name="-n",usage="repeat <n> times\nusage can have new lines in it and also it can be verrrrrrrrrrrrrrrrrry long")
    private int num = -1;

    // using 'handler=...' allows you to specify a custom OptionHandler
    // implementation class. This allows you to bind a standard Java type
    // with a non-standard option syntax
    @Option(name="-custom",handler=BooleanOptionHandler.class,usage="boolean value for checking the custom handler")
    private boolean data;

    // receives other command line parameters than options
    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        new SampleMain().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.
            if( arguments.isEmpty() )
                throw new CmdLineException("No argument is given");

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java SampleMain"+parser.printExample(ALL));

            return;
        }

        // this will redirect the output to the specified output
        System.out.println(out);

        if( recursive )
            System.out.println("-r flag is set");

        if( data )
            System.out.println("-custom flag is set");

        System.out.println("-str was "+str);

        if( num>=0 )
            System.out.println("-n was "+num);

        // access non-option arguments
        System.out.println("other arguments are:");
        for( String s : arguments )
            System.out.println(s);
    }
}