package org.kohsuke.args4j.spi;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link InetAddress} {@link OptionHandler}.
 *
 * @author Dipen Lad
 */
public class InetAddressOptionHandler extends
		OneArgumentOptionHandler<InetAddress> {
	public InetAddressOptionHandler(CmdLineParser parser, OptionDef option,
			Setter<? super InetAddress> setter) {
		super(parser, option, setter);
	}

	@Override
	protected InetAddress parse(String argument) throws CmdLineException {
		try {
			return InetAddress.getByName(argument);
		} catch (UnknownHostException e) {
			throw new CmdLineException(owner,
					Messages.ILLEGAL_IP_ADDRESS, argument);
		}
	}

	@Override
	public String getDefaultMetaVariable() {
        return Messages.DEFAULT_META_INET_ADDRESS_OPTION_HANDLER.format();
	}
}
