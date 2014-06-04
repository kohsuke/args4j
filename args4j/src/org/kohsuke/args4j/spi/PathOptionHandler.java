package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Takes a single argument to the option and maps that to {@link Path}.
 *
 * @author kmahoney
 */
@SuppressWarnings("Since15")
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
            // TODO localize me
            throw new CmdLineException(owner, "Failed to Parse Path: " + argument, e);
        }
    }

    @Override
    public String getDefaultMetaVariable() {
        return "PATH";
    }
}