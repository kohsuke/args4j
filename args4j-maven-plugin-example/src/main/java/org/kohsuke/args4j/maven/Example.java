package org.kohsuke.args4j.maven;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Example {
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
            System.err.println(e.getMessage());
            p.printUsage(System.err);
            return 1;
        }
    }

    private void run() {
        System.out.format("Option %s Required %s\n", option, required);
    }
}
