package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.CmdLineException;

/**
 * Eagerly grabs all the arguments.
 *
 * <p>
 * Used with {@link Argument}, this implements a semantics where
 * non-option token causes the option parsing to terminate.
 * An example of this is ssh(1), where "ssh -p 222 abc" will treat "-p" as an option
 * to ssh but "ssh abc -p 222" is considered to have no option for ssh.
 *
 * @author Kohsuke Kawaguchi
 */
public class RestOfArgumentsHandler extends OptionHandler<String> {
    public RestOfArgumentsHandler(CmdLineParser cmdLineParser, OptionDef optionDef, Setter<String> setter) {
        super(cmdLineParser, optionDef, setter);
    }

    public int parseArguments(Parameters parameters) throws CmdLineException {
        for (int i=0; i<parameters.size(); i++)
            setter.addValue(parameters.getParameter(i));
        return parameters.size();
    }

    public String getDefaultMetaVariable() {
        return "ARGS";
    }
}
