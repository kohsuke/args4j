package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Float} 
 * {@link OptionHandler}
 * {@link OneArgumentOptionHandler}
 * 
 * @author Jan Materne
 * @since 2.0.9
 */
public class FloatOptionHandler extends OneArgumentOptionHandler<Float> {

	public FloatOptionHandler(CmdLineParser parser, OptionDef option,
			Setter<? super Float> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Float parse(String argument) throws NumberFormatException {
		return Float.parseFloat(argument);
	}

}
