package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.OptionHandler;

/**
 * Signals an error in the user input.
 *
 * @author Kohsuke Kawaguchi
 */
public class CmdLineException extends Exception {
	private static final long serialVersionUID = -8574071211991372980L;

    private CmdLineParser parser;

    /**
     * @deprecated
     *      Use {@link #CmdLineException(CmdLineParser, String)}
     */
    public CmdLineException(String message) {
        super(message);
    }

    /**
     * @deprecated
     *      Use {@link #CmdLineException(CmdLineParser, String, Throwable)}
     */
    public CmdLineException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @deprecated
     *      Use {@link #CmdLineException(CmdLineParser, Throwable)}
     */
    public CmdLineException(Throwable cause) {
        super(cause);
    }

    public CmdLineException(CmdLineParser parser, String message) {
        super(message);
        this.parser = parser;
    }

    public CmdLineException(CmdLineParser parser, String message, Throwable cause) {
        super(message, cause);
        this.parser = parser;
    }

    public CmdLineException(CmdLineParser parser, Throwable cause) {
        super(cause);
        this.parser = parser;
    }

    /**
     * Obtains the {@link CmdLineParser} that triggered an exception.
     *
     * <p>
     * Unless you have legacy {@link OptionHandler} that doesn't pass in this information
     * when it throws an exception, this method should always return a non-null value. a
     */
    public CmdLineParser getParser() {
        return parser;
    }
}
