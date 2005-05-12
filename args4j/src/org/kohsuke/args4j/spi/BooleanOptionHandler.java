package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

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

    public String getDefaultMetaVariable() {
        return null;
    }
}
