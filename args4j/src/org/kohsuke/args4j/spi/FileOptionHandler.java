package org.kohsuke.args4j.spi;

import java.io.File;

import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * {@link File} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class FileOptionHandler extends OptionHandler<File> {
    public FileOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super File> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        setter.addValue(new File(params.getParameter(0)));
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        return "FILE";
    }
}
