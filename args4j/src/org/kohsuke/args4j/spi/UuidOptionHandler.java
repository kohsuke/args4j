package org.kohsuke.args4j.spi;

import java.util.UUID;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link UUID} {@link OptionHandler}.
 *
 * @author Tobias Stolzmann
 */
public class UuidOptionHandler extends
		OneArgumentOptionHandler<UUID> {
	public UuidOptionHandler(CmdLineParser parser, OptionDef option,
			Setter<? super UUID> setter) {
		super(parser, option, setter);
	}

	@Override
	protected UUID parse(String argument) throws CmdLineException {
		try {
			return UUID.fromString(argument);
		} catch (IllegalArgumentException e) {
			throw new CmdLineException(owner,
					Messages.ILLEGAL_UUID.format(argument));
		}
	}

	@Override
	public String getDefaultMetaVariable() {
		return "<uuid>";
	}
}
