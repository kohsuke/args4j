package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Byte}
 * {@link OptionHandler}
 * {@link OneArgumentOptionHandler}
 * @author Jan Materne
 * @since 2.0.9
 */
public class ByteOptionHandler extends OneArgumentOptionHandler<Byte> {

	public ByteOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Byte> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Byte parse(String argument) throws NumberFormatException {
		return Byte.parseByte(argument);
	}

}
