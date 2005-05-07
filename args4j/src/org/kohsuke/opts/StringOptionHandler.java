package org.kohsuke.opts;

import org.kohsuke.CmdLineParser;
import org.kohsuke.Option;
import org.kohsuke.CmdLineException;

/**
 * String {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class StringOptionHandler extends OptionHandler {
    public StringOptionHandler(CmdLineParser parser, Option option, Setter setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        setter.addValue(params.getParameter(0));
        return 1;
    }
}
