package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link OptionHandler} that parses MAC address to byte[] of length 6.
 *
 * <p>
 * The string representation of a MAC address can be of different forms, e.g.
 *
 * <pre>
 * XXXXXXXXXXXX
 * XX XX XX XX XX XX
 * XX-XX-XX-XX-XX-XX
 * XX:XX:XX:XX:XX:XX
 * </pre>
 *
 * @author Tobias Stolzmann
 */
public class MacAddressOptionHandler extends OptionHandler<byte[]> {
    public MacAddressOptionHandler(CmdLineParser parser, OptionDef option,
	    Setter<? super byte[]> setter) {
		super(parser, option, setter);
    }

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		String macString = params.getParameter(0);
		String[] macStringArray = null;

		if (macString.matches("[0-9a-fA-F]{12}"))
		    /*
		     * When entering this clause our MAC address is a hexadecimal string
		     * with 12 digit. Hence we have no delimiter to split. So we simply
		     * split after each two characters.
		     */
		    macStringArray = macString.split("(?<=\\G.{2})");
		else if (macString.matches("([0-9a-fA-F]{1,2}[^0-9a-fA-F]+){5}[0-9a-fA-F]{1,2}"))
		    /*
		     * When entering this clause our MAC address is a in the form
		     * XX#XX#XX#XX#XX#XX where XX is a hexadecimal string with one or two
		     * digits and # is a delimiter which contains no hexadecimal digit.
		     * In most cases # is a dash (-), a colon (:) or a space ( ).
		     * We just need to split by our delimiter.
		     */
		    macStringArray = macString.split("[^0-9a-fA-F]+");
		else
		    throw new CmdLineException(owner,
			    Messages.ILLEGAL_MAC_ADDRESS, macString);

		byte[] mac = new byte[6];
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
		    mac[i] = (byte) Short.parseShort(macStringArray[i], 16);

		setter.asFieldSetter().addValue(mac);		
		return 1;
	}

    @Override
    public String getDefaultMetaVariable() {
        return Messages.DEFAULT_META_MAC_ADDRESS_OPTION_HANDLER.format();
    }

    @Override
    public String print(byte[] v) {
        StringBuilder buf = new StringBuilder();
        for (byte b : v) {
            if (buf.length()>0) buf.append(':');
            buf.append(Integer.toHexString(((int)b)&0xFF));
        }
        return buf.toString();
    }
}