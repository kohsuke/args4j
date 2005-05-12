package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;

/**
 * {@link Integer} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class IntOptionHandler extends OptionHandler {
    public IntOptionHandler(CmdLineParser parser, Option option, Setter setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        setter.addValue(params.getIntParameter(0));
        return 1;
    }

    public String getDefaultMetaVariable() {
        return "N";
    }
}
