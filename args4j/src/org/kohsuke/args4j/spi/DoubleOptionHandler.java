package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Double} 
 * {@link OptionHandler}.
 * {@link OneArgumentOptionHandler}
 * @author Leif Wickland
 */
public class DoubleOptionHandler extends OneArgumentOptionHandler<Double> {
    public DoubleOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Double> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Double parse(String argument) throws NumberFormatException {
    	return Double.parseDouble(argument);
    }
}
