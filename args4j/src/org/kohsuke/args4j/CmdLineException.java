package org.kohsuke.args4j;

/**
 * Signals a failure in the command line parameters processing.
 * 
 * <p>
 * The {@link #getMessage()} method will return a human-readable
 * description of the error, so you should just do
 * <pre>
 *   System.err.println(e.getMessage());
 * </pre>
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class CmdLineException extends Exception {
    public CmdLineException() {
        super();
    }

    public CmdLineException(String message) {
        super(message);
    }
}
