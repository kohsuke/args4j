package org.kohsuke.opts;

import org.kohsuke.CmdLineException;
import org.kohsuke.CmdLineParser;
import org.kohsuke.Option;

/**
 * Boolean {@link OptionHandler}.
 * 
 * @author Kohsuke Kawaguchi
 */
public class BooleanOptionHandler extends OptionHandler {
    public BooleanOptionHandler(CmdLineParser parser, Option option, Setter setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        setter.addValue(true);
        return 0;
    }
}
