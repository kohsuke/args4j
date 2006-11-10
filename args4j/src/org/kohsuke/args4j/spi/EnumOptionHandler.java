package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

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
        String s = params.getParameter(0);
        T value = null;
        for( T o : enumType.getEnumConstants() )
            if(o.name().equalsIgnoreCase(s)) {
                value = o;
                break;
            }

        if(value==null)
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(option.toString(),s));
        setter.addValue(value);
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        String n = enumType.getName();
        int idx = n.lastIndexOf('.');
        if(idx>=0)  n = n.substring(idx+1);
        return n.toUpperCase();
    }
}
