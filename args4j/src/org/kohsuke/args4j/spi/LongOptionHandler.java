package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Byte}
 * {@link OptionHandler}
 * @author Jan Matèrne
 * @since 2.0.9
 */
public class LongOptionHandler extends OptionHandler<Long> {

	public LongOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Long> setter) {
		super(parser, option, setter);
	}

	@Override
	public String getDefaultMetaVariable() {
		return "N";
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
        String token = params.getParameter(0);
        try {
            long value = Long.parseLong(token);
            setter.addValue(value);
        }
        catch (NumberFormatException ex) {
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(option.toString(),token));
        }
        return 1;
	}

}
