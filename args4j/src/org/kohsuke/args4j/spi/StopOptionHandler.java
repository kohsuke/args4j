package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

/**
 * {@link OptionHandler} for the option terminator <tt>--</tt>.
 *
 * <p>
 * This {@link OptionHandler} can be used to implement the special token
 * <tt>--</tt> that indicates that the rest of tokens are not options, but arguments.
 *
 * <p>
 * For example, if you have the following class:
 *
 * <code><pre>
 * class Foo {
 *   &#64;Argument
 *   &#64;Option(name="--",handler={@link StopOptionHandler}.class)
 *   List&lt;String> args;
 *
 *   &#64;Option(name="-n")
 *   int n;
 * }
 * </pre></code>
 *
 * <p>
 * The command line <samp>-n 5 abc def</samp> would parse into {@code n=5, args={"abc",def"}},
 * but <samp> -- -n 5 abc def</samp> would parse into {@code n=0, args={"-n","5","abc","def"}}.
 *
 * @author Kohsuke Kawaguchi
 */
public class StopOptionHandler extends OptionHandler<String> {
    public StopOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super String> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
    	owner.stopOptionParsing();
    	return 0;
    }

    @Override
    public String getDefaultMetaVariable() {
        return Messages.DEFAULT_META_REST_OF_ARGUMENTS_HANDLER.format();
    }
}
