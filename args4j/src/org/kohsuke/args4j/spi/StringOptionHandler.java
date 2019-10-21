package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * String {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class StringOptionHandler extends OptionHandler<String> {
    public StringOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super String> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        int consumed = 0;
        String param = params.getParameter(consumed);

        if(param.startsWith("\"") || param.startsWith("'")) {
            String end = String.valueOf(param.charAt(0));
            StringBuilder builder = new StringBuilder();
            param = param.substring(1); // remove the quote-starting character
            while(!param.endsWith(end)) {
                builder.append(param); builder.append(" ");
                consumed++;
                param = params.getParameter(consumed);
            }
            builder.append(param, 0, param.length()-1); // append without the quote-ending character
            setter.addValue(builder.toString());
        } else {
            setter.addValue(param);
        }
        return consumed + 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        return Messages.DEFAULT_META_STRING_OPTION_HANDLER.format();            
    }
}
