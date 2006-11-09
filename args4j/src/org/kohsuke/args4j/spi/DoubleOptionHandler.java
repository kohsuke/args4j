package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Double} {@link OptionHandler}.
 *
 * @author Leif Wickland
 */
public class DoubleOptionHandler extends OptionHandler<Double> {
    public DoubleOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Double> setter) {
        super(parser, option, setter);
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        String token = params.getParameter(0);
        try {
            double value = Double.parseDouble(token);
            setter.addValue(value);
        }
        catch (NumberFormatException ex) {
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(option.name(),token));
        }
        return 1;
    }

    public String getDefaultMetaVariable() {
        return "N";
    }


}
