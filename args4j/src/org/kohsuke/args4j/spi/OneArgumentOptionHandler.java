package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * OptionHandler which handles an option with exactly one argument, like "-foo bar".
 * @author Jan Materne
 * @since 2.0.9
 * @param <T> Type of the Setter-class
 */
public abstract class OneArgumentOptionHandler<T> extends OptionHandler<T> {

	public OneArgumentOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super T> setter) {
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
            T value = parse(token);
            setter.addValue(value);
        }
        catch (NumberFormatException ex) {
            throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, params.getParameter(-1), token);
        }
        return 1;
	}	
	
	/**
	 * Parses a string to a real value of Type &lt;T&gt;.
	 * @param argument String value to parse
	 * @return the parsed value
	 * @throws NumberFormatException if parsing is not possible
     * @throws CmdLineException
     *      if the parsing encounters a failure that should be reported to the user.
	 */
	protected abstract T parse(String argument) throws NumberFormatException, CmdLineException;

}
