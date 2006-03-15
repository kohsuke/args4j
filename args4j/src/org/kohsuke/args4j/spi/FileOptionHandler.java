package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * {@link File} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class FileOptionHandler extends OptionHandler<File> {
    public FileOptionHandler(CmdLineParser parser, Option option, Setter<? super File> setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        setter.addValue(new File(params.getParameter(0)));
        return 1;
    }

    public String getDefaultMetaVariable() {
        return "FILE";
    }
}
