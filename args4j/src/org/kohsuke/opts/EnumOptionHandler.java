package org.kohsuke.opts;

import org.kohsuke.CmdLineParser;
import org.kohsuke.Option;
import org.kohsuke.CmdLineException;

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
        Enum value = null;
        try {
            value = Enum.valueOf(enumType,s);
        } catch (IllegalArgumentException e) {
            throw new CmdLineException(Messages.ILLEVAL_VALUE.format(s));
        }
        setter.addValue(value);
        return 1;
    }
}
