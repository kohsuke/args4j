package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;

/**
 * List of arguments.
 *
 * <p>
 * Object of this interface is passed to
 * {@link OptionHandler}s to make it easy/safe to parse
 * additional parameters for options.
 */
public interface Parameters  {
    /**
     * Gets the additional parameter to this option.
     *
     * @param idx
     *      specifying 0 will retrieve the token next to the option.
     *      For example, if the command line looks like "-o abc -d x",
     *      then <code>getParameter(0)</code> for "-o" returns "abc"
     *      and <code>getParameter(1)</code> will return "-d".
     *
     * @return
     *      Always return non-null valid String. If an attempt is
     *      made to access a non-existent index, this method throws
     *      appropriate {@link org.kohsuke.args4j.CmdLineException}.
     */
    String getParameter(int idx) throws CmdLineException;

    /**
     * Number of remaining tokens.
     */
    int size();
}
