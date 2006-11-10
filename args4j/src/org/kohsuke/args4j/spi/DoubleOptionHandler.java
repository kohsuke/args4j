package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * {@link Double} {@link OptionHandler}.
 *
 * @author Leif Wickland
 */
public class DoubleOptionHandler extends OptionHandler<Double> {
    public DoubleOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Double> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        String token = params.getParameter(0);
        try {
            double value = Double.parseDouble(token);
            setter.addValue(value);
        }
        catch (NumberFormatException ex) {
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(option.toString(),token));
        }
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        return "N";
    }


}
