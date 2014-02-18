package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Byte} {@link OptionHandler}.
 * 
 * @author Tobias Stolzmann
 */
public class MacAddressOptionHandler extends OneArgumentOptionHandler<Byte[]> {
    public MacAddressOptionHandler(CmdLineParser parser, OptionDef option,
	    Setter<? super Byte[]> setter) {
	super(parser, option, setter);
    }

    @Override
    protected Byte[] parse(String argument) throws CmdLineException {
	String[] macString = null;

	if (argument.matches("[0-9a-fA-F]{12}"))
	    /*
	     * When entering this clause our MAC address is a hexadecimal string
	     * with 12 digit. Hence we have no delimiter to split. So we simply
	     * split after each two characters.
	     */
	    macString = argument.split("(?<=\\G.{2})");
	else if (argument.matches("([0-9a-fA-F]{1,2}(-|:)){5}[0-9a-fA-F]{1,2}"))
	    /*
	     * When entering this clause our MAC address is a in the form
	     * x#x#x#x#x#x where x is a hexadecimal string with one or two
	     * digits and # is a dash (-) or a colon (:) symbolizing our
	     * delimiter. Therefore we split by our delimiter.
	     */
	    macString = argument.split("-|:");
	else
	    throw new CmdLineException(owner,
		    Messages.ILLEGAL_MAC_ADDRESS.format(argument));

	Byte[] mac = new Byte[6];
	for (int i = 0; i < 6; i++)
	    /*
	     * Yes, we really need to parse a short here... ;-)
	     * Explanation: All six MAC address parts are unsigned bytes in
	     * hexadecimal representation. They lay between 0x00 and 0xff
	     * respectively 0 and 255. The Java data type byte is signed. It
	     * lays between -128 and 127. Therefore we need to "convert" the
	     * upper half of our unsigned values to negative to obtain the
	     * correct bit representation (think of the two's complement). This
	     * is done best by parsing short (or int or long) and casting to
	     * byte.
	     */
	    mac[i] = (byte) Short.parseShort(macString[i], 16);

	return mac;
    }

    @Override
    public String getDefaultMetaVariable() {
	return "<mac address>";
    }
}