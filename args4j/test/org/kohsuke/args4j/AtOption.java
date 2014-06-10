package org.kohsuke.args4j;

@SuppressWarnings("unused")
public class AtOption {
    @Option(name="-string",usage="set a string")
    public String str = "default";
    
    @Option(name="-noUsage")
    public String noUsage;
}
