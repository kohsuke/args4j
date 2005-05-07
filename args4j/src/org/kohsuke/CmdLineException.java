package org.kohsuke;

/**
 * @author Kohsuke Kawaguchi
 */
public class CmdLineException extends Exception {
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
