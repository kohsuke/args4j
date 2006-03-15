package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * String {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class StringOptionHandler extends OptionHandler<String> {
    public StringOptionHandler(CmdLineParser parser, Option option, Setter<? super String> setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        setter.addValue(params.getParameter(0));
        return 1;
    }

    public String getDefaultMetaVariable() {
        return "VAL";
    }
}
