package org.kohsuke.args4j.spi;

import java.util.UUID;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link UUID} {@link OptionHandler}.
 *
 * @author Tobias Stolzmann
 * @see UUID#fromString(String)
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
            if (argument.startsWith("{"))   argument=argument.substring(1);
            if (argument.endsWith("}"))   argument=argument.substring(0,argument.length()-1);
			return UUID.fromString(argument);
		} catch (IllegalArgumentException e) {
			throw new CmdLineException(owner,
					Messages.ILLEGAL_UUID, argument);
		}
	}

	@Override
	public String getDefaultMetaVariable() {
            return Messages.DEFAULT_META_UUID_OPTION_HANDLER.format();            
	}
}
