package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: kmahoney
 * Date: 6/6/13
 * Time: 8:17 AM
 */
public class PathOptionHandler extends OneArgumentOptionHandler<Path> {
    public PathOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Path> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Path parse(String argument) throws NumberFormatException, CmdLineException {
        try {
        return Paths.get(argument);
        }
        catch (Exception e) {
            throw new CmdLineException(owner, "Failed to Parse Path: " + argument, e);
        }
    }

    @Override
    public String getDefaultMetaVariable() {
        return "PATH";
    }
}