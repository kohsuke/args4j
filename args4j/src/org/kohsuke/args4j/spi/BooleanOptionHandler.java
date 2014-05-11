package org.kohsuke.args4j.spi;

import java.util.Arrays;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * Boolean {@link OptionHandler}.
 * 
 * @author Kohsuke Kawaguchi
 */
public class BooleanOptionHandler extends OptionHandler<Boolean> {
	private static final List<String> ACCEPTABLE_VALUES = Arrays.asList(new String[] { "true", "on", "yes", "1",
			"false", "off", "no", "0" });
	
	public BooleanOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Boolean> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
    	if (option.isArgument()) {
    		String valueStr = params.getParameter(0).toLowerCase();
    		int index = ACCEPTABLE_VALUES.indexOf(valueStr);
    		if (index == -1) {
    			throw new CmdLineException(owner, Messages.ILLEGAL_BOOLEAN, valueStr);
    		}
    		setter.addValue(index < ACCEPTABLE_VALUES.size() / 2);
    		return 1;
    	} else {
    		setter.addValue(true);
    		return 0;
    	}
    }

    @Override
    public String getDefaultMetaVariable() {
        return null;
    }
}
