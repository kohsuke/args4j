package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * <p>
 * An {@link OptionHandler} for greedily mapping a list of tokens into a collection of {@link String}s
 * (such as {@code String[]}, {@code List<String>}, etc.).
 * </p>
 *
 * <h1>How it works:</h1>
 *
 * <p>
 * Example for parameter {@code -s}, which is type {@code String[]}:</p>
 *
 * <pre>{@code
 * java -jar aaa.jar -s banan hruska jablko
 * java -jar aaa.jar -s banan "hruska jablko"
 * java -jar aaa.jar -s "banan hruska jablko"
 * java -jar aaa.jar -s banan hruska jablko -l 4 -r
 * java -jar aaa.jar -t 222 -s banan hruska jablko -r
 * }</pre>
 *
 * <p>
 * All of them result in a single string array that contains three tokens: 
 * <code>banan</code>, <code>hruska</code>, and <code>jablko</code>.</p>
 *
 * <p>
 * This {@code OptionHandler} scans for parameter which begins with <tt>-</tt>. If found, it will stop.</p>
 *
 * @author PlainText,LuVar
 */
public class StringArrayOptionHandler extends OptionHandler<String> {

	public StringArrayOptionHandler(CmdLineParser parser, OptionDef option, Setter<String> setter) {
		super(parser, option, setter);
	}

	/**
	 * Returns {@code "STRING[]"}.
	 *
	 * @return	return "STRING[]";
	 */
	@Override
	public String getDefaultMetaVariable() {
            return Messages.DEFAULT_META_STRING_ARRAY_OPTION_HANDLER.format();            
	}

	/**
	 * Tries to parse {@code String[]} argument from {@link Parameters}.
	 */
	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
        int counter=0;
		for (; counter<params.size(); counter++) {
			String param = params.getParameter(counter);

            if(param.startsWith("-")) {
				break;
			}

            for (String p : param.split(" ")) {
                setter.addValue(p);
            }
		}

        return counter;
	}

}