package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Integer} {@link OptionHandler}.
 * 
 * @author Kohsuke Kawaguchi
 */
public class IntOptionHandler extends OptionHandler<Integer> {
	public IntOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Integer> setter) {
		super(parser, option, setter);
	}

    @Override
	public int parseArguments(Parameters params) throws CmdLineException {
		String token = params.getParameter(0);
		try {
			int value = Integer.parseInt(token);
			setter.addValue(value);
		} catch (NumberFormatException e) {
			throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(option.name(), token));
		}
		return 1;
	}

    @Override
	public String getDefaultMetaVariable() {
		return "N";
	}
}
