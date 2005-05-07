package org.kohsuke.opts;

import org.kohsuke.CmdLineParser;
import org.kohsuke.Option;
import org.kohsuke.CmdLineException;

/**
 * {@link Enum} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class EnumOptionHandler extends OptionHandler {
    public EnumOptionHandler(CmdLineParser parser, Option option, Setter setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        String s = params.getParameter(0);
        // parse this into enum
        return 1;
    }
}
