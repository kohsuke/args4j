package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Integer} 
 * {@link OptionHandler}.
 * {@link OneArgumentOptionHandler}
 * 
 * @author Kohsuke Kawaguchi
 */
public class IntOptionHandler extends OneArgumentOptionHandler<Integer> {
	public IntOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Integer> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Integer parse(String argument) throws NumberFormatException {
		return Integer.parseInt(argument);
	}
}
