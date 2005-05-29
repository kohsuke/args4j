package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;

/**
 * {@link Enum} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class EnumOptionHandler extends OptionHandler {

    private final Class<? extends Enum<?>> enumType;

    public EnumOptionHandler(CmdLineParser parser, Option option, Setter setter, Class<? extends Enum<?>> enumType) {
        super(parser, option, setter);
        this.enumType = enumType;
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        String s = params.getParameter(0);
        Enum value = null;
        for( Enum o : enumType.getEnumConstants() )
            if(o.name().equalsIgnoreCase(s)) {
                value = o;
                break;
            }

        if(value==null)
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(params.getOptionName(),s));
        setter.addValue(value);
        return 1;
    }

    public String getDefaultMetaVariable() {
        String n = enumType.getName();
        int idx = n.lastIndexOf('.');
        if(idx>=0)  n = n.substring(idx+1);
        return n.toUpperCase();
    }
}
