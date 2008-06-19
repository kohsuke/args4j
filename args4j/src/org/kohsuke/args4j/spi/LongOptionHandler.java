package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Long}
 * {@link OptionHandler}
 * {@link OneArgumentOptionHandler}
 * @author Jan Materne
 * @since 2.0.9
 */
public class LongOptionHandler extends OneArgumentOptionHandler<Long> {

	public LongOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Long> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Long parse(String argument) throws NumberFormatException {
		return Long.parseLong(argument);
	}

}
