package org.kohsuke.args4j;

public class Aliased {
    @Option(name="-str",aliases={ "--long-str" },usage="set a string",metaVar="METAVAR")
    public String str;
}
