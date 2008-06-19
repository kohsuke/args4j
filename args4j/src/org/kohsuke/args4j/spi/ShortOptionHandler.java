package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Short}
 * {@link OptionHandler}
 * {@link OneArgumentOptionHandler}
 * @author Jan Materne
 * @since 2.0.9
 */
public class ShortOptionHandler extends OneArgumentOptionHandler<Short> {

	public ShortOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Short> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Short parse(String argument) throws NumberFormatException {
		return Short.parseShort(argument);
	}

}
