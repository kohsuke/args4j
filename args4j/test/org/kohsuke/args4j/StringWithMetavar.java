package org.kohsuke.args4j;

import org.kohsuke.args4j.Option;

public class StringWithMetavar {
    @Option(name="-str",usage="set a string",metaVar="METAVAR")
    public String str;
}
