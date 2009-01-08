package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

import java.util.ArrayList;

/**
 * <p>
 * An {@link OptionHandler} for handling {@link String[]} types. Can handle arrays of strings.
 * </p>
 * <p>
 * <h1>How it works:</h1>
 * Example for parameter -s, which is String[] type:<br>
 * java -jar aaa.jar -s banan hruska jablko<br>
 * java -jar aaa.jar -s banan "hruska jablko"<br>
 * java -jar aaa.jar -s "banan hruska jablko"<br>
 * java -jar aaa.jar -s banan hruska jablko -l 4 -r<br>
 * java -jar aaa.jar -t 222 -s banan hruska jablko -r<br><br>
 * It will handle all of these posibilites. This OptionHandler scans for parameter which begins
 * with "-". If it found it, it will stop.
 * </p>
 *
 * @author PlainText,LuVar
 *
 */
public class StringArrayOptionHandler extends OptionHandler<String[]> {

	public StringArrayOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super String[]> setter) {
		super(parser, option, setter);
	}

	/**
	 * <p>
	 * Returns "STRING[]".
	 * </p>
	 *
	 * @return	return "STRING[]";
	 */
	@Override
	public String getDefaultMetaVariable() {
		return "STRING[]";
	}

	/**
	 * <p>
	 * Tryies to parse String[] argument from {@link Parameters}.
	 * </p>
	 */
	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		int counter = 0;
		ArrayList<String> values = new ArrayList<String>();
		while(true) {
			String param;
			try {
				param = params.getParameter(counter);
			} catch (CmdLineException ex) {
				break;
			}
			if(param.startsWith("-")) {
				break;
			}

			for (String str : param.split(" ")) {
				values.add(str);
			}
			counter++;
		}//while true

        // to work around a javac bug in Java1.5, we need to first assign this to
        // the raw type.
        Setter s = this.setter;
        s.addValue(values.toArray(new String[values.size()]));
		return counter;
	}

}