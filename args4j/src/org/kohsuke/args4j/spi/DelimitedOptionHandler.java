package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * Created with IntelliJ IDEA.
 * User: kmahoney
 * Date: 6/19/13
 * Time: 6:17 AM
 */
public class DelimitedOptionHandler<T> extends OptionHandler<T> {

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