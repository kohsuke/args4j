package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.OptionHandler;

import java.io.Writer;
import java.util.ResourceBundle;

/**
 * Selects {@link OptionHandler}.
 *
 * <p>
 * For example, we use this to let the caller specify which 
 * options are printed, and which ones aren't.
 *
 * @author Kohsuke Kawaguchi
 * @see CmdLineParser#printExample(OptionHandlerFilter)
 * @see CmdLineParser#printUsage(Writer, ResourceBundle, OptionHandlerFilter)
 */
public interface OptionHandlerFilter {
    /**
     *
     * @param o
     *      Never {@code null}. Internally options (like <code>-r</code>) and arguments (others)
     *      are treated uniformly as {@link OptionHandler}. 
     *      See {@link OptionDef#isArgument()} to distinguish them.
     * @return
     *      true to choose this option, false to ignore/discard/disregard it.
     */
    boolean select(OptionHandler o);

    /**
     * Print all defined options in the example.
     */
    OptionHandlerFilter ALL = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return true;
        }
    };

    /**
     * Print all {@linkplain Option#hidden() non-hidden} options.
     *
     * <p>
     * This would only be useful with a small number of options.
     */
    OptionHandlerFilter PUBLIC = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return !o.option.hidden();
        }
    };

    /**
     * Print all {@linkplain Option#required() required} options.
     */
    OptionHandlerFilter REQUIRED = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return o.option.required();
        }
    };
}
