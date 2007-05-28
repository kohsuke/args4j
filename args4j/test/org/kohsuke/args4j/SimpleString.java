package org.kohsuke.args4j;

import org.kohsuke.args4j.Option;

@SuppressWarnings("unused")
public class SimpleString {
    @Option(name="-str",usage="set a string")
    public String str = "default";
    
    @Option(name="-nu")
    public String noUsage;
}
