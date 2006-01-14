package org.kohsuke.args4j;

import java.util.Map;

public class LongUsage {

    @Option(name="-LongNamedStringOption",usage="set a string",metaVar="USE_A_NICE_STRING")
    private String s;

    @Option(name="-i",usage="set an int")
    private int i;

    //TODO implement parsing properties
    //@Property(prefix="-P",usage="sets a key-value-pair")
    private Map props;

}