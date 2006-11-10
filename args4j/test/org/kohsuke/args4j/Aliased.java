package org.kohsuke.args4j;

import org.kohsuke.args4j.Option;

public class Aliased {
    @Option(name="-str",aliases={ "--long-str" },usage="set a string",metaVar="METAVAR")
    public String str;
}
