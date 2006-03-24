package org.kohsuke.args4j;

/**
 * Test object for long option names.
 * One short and one long name to 'see' the differences.
 *
 * @author Jan Materne
 */
@SuppressWarnings("unused")
public class LongUsage {

    @Option(name="-LongNamedStringOption",usage="set a string",metaVar="USE_A_NICE_STRING")
    private String s;

    @Option(name="-i",usage="set an int")
    private int i;

}