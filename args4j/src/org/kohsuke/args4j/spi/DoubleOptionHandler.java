package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;

/**
 * {@link Double} {@link OptionHandler}.
 *
 * @author Leif Wickland
 */
public class DoubleOptionHandler extends OptionHandler {
    public DoubleOptionHandler(CmdLineParser parser, Option option, Setter setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        String token = params.getParameter(0);
        try {
            double value = Double.parseDouble(token);
            setter.addValue(value);
        }
        catch (NumberFormatException ex) {
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(params.getOptionName(), token));
        }
        return 1;
    }

    public String getDefaultMetaVariable() {
        return "N";
    }


}
