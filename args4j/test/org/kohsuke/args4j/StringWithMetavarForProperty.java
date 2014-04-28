package org.kohsuke.args4j;

public class StringWithMetavarForProperty {
    @Option(name="-str",property = true, usage="set a string",metaVar="METAVAR")
    public String str;
}
