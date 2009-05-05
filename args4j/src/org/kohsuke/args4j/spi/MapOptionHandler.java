package org.kohsuke.args4j.spi;

import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.MapSetter;
import org.kohsuke.args4j.OptionDef;

public class MapOptionHandler extends OptionHandler<Map<?,?>> {

	public MapOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Map<?,?>> setter) {
		super(parser, option, setter);
	}

	@Override
	public String getDefaultMetaVariable() {
		return null;
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		MapSetter mapSetter = (MapSetter)setter;
		try {
			mapSetter.addValue(params.getParameter(0));
		} catch (RuntimeException e) {
			throw new CmdLineException(owner, e.getMessage());
		}
        return 1;
	}

}
