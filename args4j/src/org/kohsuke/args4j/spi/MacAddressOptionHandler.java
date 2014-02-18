package org.kohsuke.args4j.spi;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Byte} {@link OptionHandler}.
 *
 * @author Tobias Stolzmann
 */
public class MacAddressOptionHandler extends
		OneArgumentOptionHandler<Byte[]> {
	public MacAddressOptionHandler(CmdLineParser parser, OptionDef option,
			Setter<? super Byte[]> setter) {
		super(parser, option, setter);
	}

	@Override
	protected Byte[] parse(String argument) throws CmdLineException {
		String[] macString = null;
		
		if (argument.matches("[0-9a-fA-F]{12}"))
			macString = argument.split("(?<=\\G.{2})");
		else if (argument.matches("([0-9a-fA-F]{1,2}(-|:)){5}[0-9a-fA-F]{1,2}"))
			macString = argument.split("-|:");
		else
			throw new CmdLineException(owner,
					Messages.ILLEGAL_MAC_ADDRESS.format(argument));
		
		Byte[] mac = new Byte[6];
		for (int i = 0; i < 6; i++)
			mac[i] = (byte) Short.parseShort(macString[i], 16);
			
		return mac;
	}

	@Override
	public String getDefaultMetaVariable() {
		return "<mac address>";
	}
}