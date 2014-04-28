package org.kohsuke.args4j;

public class StringWithMetavar {
    @Option(name="-str",usage="set a string",metaVar="METAVAR")
    public String str;
}
