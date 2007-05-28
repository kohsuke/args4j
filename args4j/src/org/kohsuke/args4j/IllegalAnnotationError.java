package org.kohsuke.args4j;

/**
 * Signals an incorrect use of args4j annotations.
 *
 * <p>
 * This only happens when there's something wrong with the way you use
 * args4j in your code, not when the arguments supplied by the user is
 * wrong. Hence this class is an {@link Error}.
 *
 * @author Kohsuke Kawaguchi
 */
public class IllegalAnnotationError extends Error {
	private static final long serialVersionUID = 2397757838147693218L;

	public IllegalAnnotationError(String message) {
        super(message);
    }

    public IllegalAnnotationError(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAnnotationError(Throwable cause) {
        super(cause);
    }
}
