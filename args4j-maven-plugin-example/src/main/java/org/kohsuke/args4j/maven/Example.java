package org.kohsuke.args4j.maven;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Example {
	private static final Logger LOGGER = Logger.getLogger(Example.class.getName());
	
    @Option(name = "-o", usage="Option")
    public String option;

    @Option(name = "-r", required = true, usage="Required")
    public String required;

    public static void main(String[] args) {
        System.exit(new Example().run(args));
    }

    private int run(String[] args) {
        CmdLineParser p = new CmdLineParser(this);
        try {
            p.parseArgument(args);
            run();
            return 0;
        } catch (CmdLineException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            p.printUsage(System.err);
            return 1;
        }
    }

    private void run() {
        LOGGER.log(Level.FINE, "Option {0} Required {1}\n", new Object[] {option, required});
    }
}
