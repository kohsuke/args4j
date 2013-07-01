package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: kmahoney
 * Date: 6/19/13
 * Time: 6:17 AM
 */
public class MultiFileOptionHandler extends DelimitedOptionHandler<File> {
    protected static String sysPathSeperator = System.getProperty("path.separator");
    public MultiFileOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super File> setter) {
        super(parser, option, setter, sysPathSeperator, new FileOptionHandler(parser, option, setter));
    }
}