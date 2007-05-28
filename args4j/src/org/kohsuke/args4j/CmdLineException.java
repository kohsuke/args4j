package org.kohsuke.args4j;

/**
 * Signals an error in the user input.
 *
 * @author Kohsuke Kawaguchi
 */
public class CmdLineException extends Exception {
	private static final long serialVersionUID = -8574071211991372980L;

	public CmdLineException(String message) {
        super(message);
    }

    public CmdLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public CmdLineException(Throwable cause) {
        super(cause);
    }
}
