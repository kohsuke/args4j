package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


/**
 * @author Kohsuke Kawaguchi
 */
public abstract class OptionHandler {
    /**
     * The annotation.
     */
    public final Option option;
    /**
     * Object to be used for setting value.
     */
    public final Setter setter;
    /**
     * The owner to which this handler belongs to.
     */
    public final CmdLineParser owner;

    protected OptionHandler(CmdLineParser parser, Option option, Setter setter) {
        this.owner = parser;
        this.option = option;
        this.setter = setter;
    }

    /**
     * Called if the option that this owner recognizes is found.
     *
     * @param params
     *      The rest of the arguments. This method can use this
     *      object to access the arguments of the option if necessary.
     *
     *      The object is valid only during the method call.
     *
     * @return
     *      The number of arguments consumed. For example, return 0
     *      if this option doesn't take any parameter.
     */
    public abstract int parseArguments( Parameters params ) throws CmdLineException;
}
