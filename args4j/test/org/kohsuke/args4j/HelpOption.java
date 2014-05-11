package org.kohsuke.args4j;

public class HelpOption {
    @Option(name="-req",usage="required option",required = true)
    public String req1;
    
    @Option(name="-opt",usage="optional option")
    public String optional1;
    
    @Option(name="-help",help = true)
    public boolean help;
}
