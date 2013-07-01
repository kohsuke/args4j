package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

import java.io.File;

/**
 * Takes a classpath like option ("-cp a.jar;b.jar;c") and maps them to a collection of {@link File}.
 *
 * @author kmahoney
 */
public class MultiFileOptionHandler extends DelimitedOptionHandler<File> {
    protected static String sysPathSeperator = System.getProperty("path.separator");
    public MultiFileOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super File> setter) {
        super(parser, option, setter, sysPathSeperator, new FileOptionHandler(parser, option, setter));
    }
}