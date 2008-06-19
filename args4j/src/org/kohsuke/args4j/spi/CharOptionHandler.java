package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Char}
 * {@link OptionHandler}
 * {@link OneArgumentOptionHandler}
 * @author Jan Materne
 * @since 2.0.9
 */
public class CharOptionHandler extends OneArgumentOptionHandler<Character> {

	public CharOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Character> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Character parse(String argument) throws NumberFormatException {
		return argument.charAt(0);
	}
}
