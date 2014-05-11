package org.kohsuke.args4j;

public class HiddenOption {
    @Option(name="-str",usage="set a string",metaVar="METAVAR",hidden=true)
    public String str;
}
