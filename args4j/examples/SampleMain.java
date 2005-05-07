import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.MissingRequiredArgumentException;
import org.kohsuke.args4j.opts.BooleanCmdLineItem;
import org.kohsuke.args4j.opts.IntCmdLineItem;
import org.kohsuke.args4j.opts.OutputStreamCmdLineItem;
import org.kohsuke.args4j.opts.StringCmdLineItem;
import org.kohsuke.args4j.opts.StringsCmdLineItem;


/**
 * Sample program that shows how you can use args4j.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class SampleMain {
    
    //
    // option constants.
    //
    // define three options. -r, -o <file>, -str <token>, and -n <num>.
    public BooleanCmdLineItem recursive = new BooleanCmdLineItem("-r","recursively run something");
    public OutputStreamCmdLineItem out  = new OutputStreamCmdLineItem("-o","output to this file","-");
    // you can also specify the default value
    public StringCmdLineItem str        = new StringCmdLineItem("-str","a random string argument","(default value)");
    public IntCmdLineItem num           = new IntCmdLineItem("-n","repeat <n> times");
    
    public StringsCmdLineItem sargs   = new StringsCmdLineItem("arg","meaningless arguments");
    
    public static void main(String[] args) throws IOException {
        new SampleMain().doMain(args);
    }
    
    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser();
        if(false) {
            // you can either add options individually
            parser.addOption(recursive);
            parser.addOption(out);
            parser.addOption(str);
            parser.addOption(num);
            parser.addArgument(sargs);
        } else {
            // or you can add all the CmdLineOption fields of the specified
            // object into the parser.
            parser.addOptionClass(this);
            // but you still need to add arguments manually,
            // because order of arguments matter.
            parser.addArgument(sargs);
        }
        
        try {
            // parse the arguments.
            parser.parse(args);
            
            // you can parse more arguments if you want.
            // parser.parse(new String[]{"more","args"});
            
            // after parsing arguments, you should check
            // if enough arguments are given.
            if( sargs.values.isEmpty() )
                throw new MissingRequiredArgumentException();
            
        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get an exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            parser.printUsage("java SampleMain",new PrintWriter(System.err));
            return;
        }
        
        // this will redirect the output to the specified output
        System.setOut(new PrintStream(out.createOutputStream()));
        
        if( recursive.isOn() )
            System.out.println("-r flag is set");
        
        System.out.println("-str was "+str.value);

        if( num.isSet )
            System.out.println("-n was "+num.value);
        
        // access non-option arguments
        System.out.println("other arguments are:");
        List rest = sargs.values;
        for( int i=0; i<rest.size(); i++ )
            System.out.println(rest.get(i));
    }
}
