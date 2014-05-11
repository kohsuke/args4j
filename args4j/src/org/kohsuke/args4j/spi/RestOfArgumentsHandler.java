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
 * An example of this is <tt>ssh(1)</tt>, where <samp>ssh -p 222 abc</samp> will treat 
 * <samp>-p</samp> as an option to <tt>ssh</tt>, but <samp>ssh abc -p 222</samp> is 
 * considered to have no option for <tt>ssh</tt>.
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
        return Messages.DEFAULT_META_REST_OF_ARGUMENTS_HANDLER.format();
    }
}
