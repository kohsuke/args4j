/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kohsuke.args4j.spi;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * A regex option handler.
 * @author Stephan Fuhrmann
 */
public class PatternOptionHandler extends OptionHandler<Pattern> {

	public PatternOptionHandler(CmdLineParser parser, OptionDef option, Setter<Pattern> setter) {
        super(parser, option, setter);
    }

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		String s = params.getParameter(0);
		Pattern p;
		try {
			p = Pattern.compile(s);
		}
		catch (PatternSyntaxException x) {
			throw new CmdLineException(owner, Messages.ILLEGAL_PATTERN.format(option.toString(), s));
		}
		setter.addValue(p);
		return 1;
	}

	@Override
	public String getDefaultMetaVariable() {
            return Messages.DEFAULT_META_PATTERN_OPTION_HANDLER.format();
	}
	
}
