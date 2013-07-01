package org.kohsuke.args4j;

import org.kohsuke.args4j.Option;

public class HiddenOption {
    @Option(name="-str",usage="set a string",metaVar="METAVAR",hidden=true)
    public String str;
}
