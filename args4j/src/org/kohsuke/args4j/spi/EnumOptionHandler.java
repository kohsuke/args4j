package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link Enum} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class EnumOptionHandler<T extends Enum<T>> extends OptionHandler<T> {

    private final Class<T> enumType;

    public EnumOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super T> setter, Class<T> enumType) {
        super(parser, option, setter);
        this.enumType = enumType;
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        String s = params.getParameter(0).replaceAll("-", "_");
        T value = null;
        for( T o : enumType.getEnumConstants() )
            if(o.name().equalsIgnoreCase(s)) {
                value = o;
                break;
            }

        if(value==null) {
            if (option.isArgument()) {
                throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND.format(option.toString(),s));
            } else {
                throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND.format(params.getParameter(-1),s));
            }
        }
        setter.addValue(value);
        return 1;
    }

    /* 
     * Returns all values of an enum type split by pipe.
     * <tt>[ one | two | three]</tt>
     * @see org.kohsuke.args4j.spi.OptionHandler#getDefaultMetaVariable()
     */
    @Override
    public String getDefaultMetaVariable() {
    	StringBuffer rv = new StringBuffer();
    	rv.append("[");
    	for (T t : enumType.getEnumConstants()) {
			rv.append(t).append(" | ");
		}
    	rv.delete(rv.length()-3, rv.length());
    	rv.append("]");
    	return rv.toString();
    }
}
