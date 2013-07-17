package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionDef;

/**
 * Partial {@link OptionHandler} implementation that takes a single value to the option,
 * which is then gets split into individual tokens using fixed delimiter.
 *
 * <p>
 * This class is marked as {@code abstract} even though it has no abstract methods
 * to indicate that the class cannot be used by itself in {@link Option#handler()},
 * due to the extra argument that it takes.
 *
 * @author kmahoney
 */
public abstract class DelimitedOptionHandler<T> extends OptionHandler<T> {

    protected final String delimiter;
    protected final OneArgumentOptionHandler<? extends T> individualOptionHandler;

    public DelimitedOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super T> setter, String delimiter, OneArgumentOptionHandler<? extends T> individualOptionHandler) {
        super(parser, option, setter);
        this.delimiter = delimiter;
        this.individualOptionHandler = individualOptionHandler;
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        String full = params.getParameter(0);
        String[] delimitedStrs = full.split(delimiter);
        for (String delimitedStr : delimitedStrs) {
            setter.addValue(individualOptionHandler.parse(delimitedStr));
        }

        // The number of Parameters consumed (not the number set)
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        final String tMetaVar = individualOptionHandler.getDefaultMetaVariable();
        if (tMetaVar == null || tMetaVar.trim().isEmpty()) return tMetaVar;
        return "<" + tMetaVar  + delimiter + tMetaVar + delimiter + "...>";
    }
}