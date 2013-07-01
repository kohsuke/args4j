package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: kmahoney
 * Date: 6/19/13
 * Time: 6:17 AM
 */
public class MultiPathOptionHandler extends DelimitedOptionHandler<Path> {
    protected static String sysPathSeperator = System.getProperty("path.separator");
    public MultiPathOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Path> setter) {
        super(parser, option, setter, sysPathSeperator, new PathOptionHandler(parser, option, setter));
    }
}