package org.kohsuke.args4j.spi;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * Boolean {@link OptionHandler} that (unlike the standard {@link BooleanOptionHandler}
 * allows values to be set to false explicitly (using e.g. '-myOpt false') rather
 * than only returning false when the option is <em>omitted</em>.
 */
public class ExplicitBooleanOptionHandler extends OptionHandler<Boolean> {
    // same values as BooleanOptionHandler
    private static final Map<String, Boolean> ACCEPTABLE_VALUES;
    
    static {
        // I like the trick in BooleanOptionHandler but find this explicit map more readable
        ACCEPTABLE_VALUES = new HashMap<String, Boolean>();
        ACCEPTABLE_VALUES.put("true", TRUE);
        ACCEPTABLE_VALUES.put("on", TRUE);
        ACCEPTABLE_VALUES.put("yes", TRUE);
        ACCEPTABLE_VALUES.put("1", TRUE);
        ACCEPTABLE_VALUES.put("false", FALSE);
        ACCEPTABLE_VALUES.put("off", FALSE);
        ACCEPTABLE_VALUES.put("no", FALSE);
        ACCEPTABLE_VALUES.put("0", FALSE);
    }
        
    public ExplicitBooleanOptionHandler(CmdLineParser parser,
            OptionDef option, Setter<? super Boolean> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        // end of arg list or next arg is another option
        if ((params.size() == 0) || params.getParameter(0).startsWith("-")) {
            setter.addValue(TRUE);
            return 0;
        } else {
            setter.addValue(getBoolean(params.getParameter(0)));
            return 1;
        }
    }

    private Boolean getBoolean(String parameter) throws CmdLineException {
        String valueStr = parameter.toLowerCase();
        if (!ACCEPTABLE_VALUES.containsKey(valueStr)) {
            throw new CmdLineException(owner, Messages.ILLEGAL_BOOLEAN.format(valueStr));
        }
        return ACCEPTABLE_VALUES.get(valueStr);
    }

    @Override
    public String getDefaultMetaVariable() {
        return "[VAL]";
    }
}
