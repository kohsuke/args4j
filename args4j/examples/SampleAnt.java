import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.TreeMap;

/**
 * Showing the Ant command line interface as args4j annotations.
 *
 * @author
 *      Jan Matèrne
 */
// Field are are access by args4j per reflection. The compiler does not know that.
// So catch these compiler warnings.
@SuppressWarnings("unused")  
public class SampleAnt {

    // 'normal' use
    @Option(name="-help",usage="print this message")
    private boolean help = false;

    // Ant uses abbreviations - a second option to set the
    // same attribute. Because only one @Option is allowed per element,
    // we use a workaround: a setter gets the abbreviation
    // A 'usage' would duplicate the info message, but without the possibility
    // of '-h' is hidden.
    @Option(name="-h")
    private void setHelp(boolean h) { help = h; }



    @Option(name="-projecthelp",usage="print project help information")
    private boolean projecthelp;

    @Option(name="-p")
    private void setProjecthelp(boolean p) { projecthelp = p; }

    @Option(name="-version",usage="print the version information and exit")
    private boolean version;

    @Option(name="-diagnostics",usage="print information that might be helpful to\ndiagnose or report problems.")
    private boolean diagnostics;

    @Option(name="-quiet",usage="be extra quiet")
    private boolean quiet;

    @Option(name="-q")
    private void setQuiet(boolean q) { quiet = q; }

    @Option(name="-verbose",usage="be extra verbose")
    private boolean verbose;

    @Option(name="-v")
    private void setVerbose(boolean v) { verbose = v; }

    @Option(name="-debug",usage="print debugging information")
    private boolean debug;

    @Option(name="-d")
    private void setDebug(boolean d) { debug = d; }

    @Option(name="-emacs",usage="produce logging information without adornments")
    private boolean emacs;

    @Option(name="-e")
    private void setEmacs(boolean e) { emacs = e; }

    @Option(name="-lib",usage="specifies a path to search for jars and classes",metaVar="<path>")
    private void setLib(String s) {
        String[] files = s.split(System.getProperty("path.separator"));
        for (int i=0; i<files.length; i++) {
            lib.add(new File(files[i]));
        }
    }
    private List<File> lib = new Vector<File>();

    @Option(name="-logger",usage="the class which is to perform logging",metaVar="<classname>")
    private String logger;

    @Option(name="-listener",usage="the class which is to perform",metaVar="<classname>")
    private String listener;

    @Option(name="-noinput",usage="do not allow interactive input")
    private boolean noinput;

    @Option(name="-buildfile",usage="use given buildfile",metaVar="<file>")
    private File buildfile;

    // Ant's original is
    //  -buildfile <file>      use given buildfile
    //    -file    <file>              ''
    //    -f       <file>              ''
    // How to handle this? Args4J prints the arguments sorted alphabetically.
    @Option(name="-file",usage="        ''",metaVar="<file>")
    private void setBuildfile1(File f) { buildfile = f; }

    @Option(name="-f",usage="        ''",metaVar="<file>")
    private void setBuildfile2(File f) { buildfile = f; }

    //TODO: How to handle that?
    //   -D<property>=<value>   use value for given property
    private TreeMap<String,String> props = new TreeMap<String,String>();

    @Option(name="-keep-going",usage="execute all targets that do not depend\non failed target(s)")
    private boolean keepGoing;

    @Option(name="-k")
    private void setKeepGoing(boolean kg) { keepGoing = kg; }

    @Option(name="-propertyfile",usage="load all properties from file with -D\nproperties taking precedence",metaVar="<name>")
    private File propertyfile;

    @Option(name="-inputhandler",usage="the class which will handle input requests",metaVar="<class>")
    private String inputhandler;

    //TODO: Another problem
    //  -find <file>           (s)earch for buildfile towards the root of
    //    -s  <file>           the filesystem and use it
    //problem is: info text has two lines and there are two lines for different option strings
    @Option(name="-find",usage="(s)earch for buildfile towards the root of\nthe filesystem and use it",metaVar="<file>")
    private File file;

    @Option(name="-s",metaVar="<file>")
    private void setFile(File f) { file = f; }

    @Option(name="-nice",usage="A niceness value for the main thread:\n1 (lowest) to 10 (highest); 5 is the default")
    private void setNice(int n) {
        if (n>0 && n<11) nice = n;
    }
    private int nice = 5;

    @Option(name="-nouserlib",usage="Run ant without using the jar files from\n${user.home}/.ant/lib")
    private boolean nouserlib;

    @Option(name="-noclasspath",usage="Run ant without using CLASSPATH")
    private boolean noclasspath;

    @Argument
    private List<String> targets;



    public static void main(String[] args) throws IOException {
        SampleAnt bean = new SampleAnt();
        try {
            bean.parseArgs(args);
            bean.showResult();
        } catch (IOException ioe) {
            //no-op
        }
    }

    public void parseArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch( CmdLineException e ) {
            int start = e.getMessage().indexOf('"')+1;
            int end   = e.getMessage().lastIndexOf('"');
            String wrongArgument = e.getMessage().substring(start, end);
            System.err.println("Unknown argument: " + wrongArgument);
            System.err.println("ant [options] [target [target2 [target3] ...]]");
            parser.printUsage(System.err);
            System.err.println();
            throw new IOException();
        }
    }

    public void showResult() {
        System.out.println("SampleAnt was configured with...");
        System.out.println("  help         : " + help);
        System.out.println("  projecthelp  : " + projecthelp);
        System.out.println("  version      : " + version);
        System.out.println("  diagnostics  : " + diagnostics);
        System.out.println("  quiet        : " + quiet);
        System.out.println("  verbose      : " + verbose);
        System.out.println("  debug        : " + debug);
        System.out.println("  emacs        : " + emacs);
        System.out.println("  lib");
        for (File f : lib) {
            System.out.println("    - " + f);
        }
        System.out.println("  logger       : " + logger);
        System.out.println("  listener     : " + listener);
        System.out.println("  noinput      : " + noinput);
        System.out.println("  buildfile    : " + buildfile);
        System.out.println("  properties");
        for (String key : props.keySet()) {
            System.out.println("    - " + key + " = " + props.get(key));
        }
        System.out.println("  keepGoing    : " + keepGoing);
        System.out.println("  propertyfile : " + propertyfile);
        System.out.println("  inputhandler : " + inputhandler);
        System.out.println("  buildfile    : " + file);
        System.out.println("  nice         : " + nice);
        System.out.println("  nouserlib    : " + nouserlib);
        System.out.println("  noclasspath  : " + noclasspath);
        System.out.println("");
        System.out.println("  targets");
        for (String t : targets) {
            System.out.println("    - " + t);
        }
        System.out.println("");
    }

}
