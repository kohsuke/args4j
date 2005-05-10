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

    private final Class<? extends Enum> enumType;

    public EnumOptionHandler(CmdLineParser parser, Option option, Setter setter, Class<? extends Enum> enumType) {
        super(parser, option, setter);
        this.enumType = enumType;
    }

    public int parseArguments(Parameters params) throws CmdLineException {
        String s = params.getParameter(0);
        Enum value;
        try {
            value = Enum.valueOf(enumType,s);
        } catch (IllegalArgumentException e) {
            throw new CmdLineException(Messages.ILLEGAL_OPERAND.format(params.getOptionName(),s));
        }
        setter.addValue(value);
        return 1;
    }
}
