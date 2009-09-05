package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Argument;

/**
 * Implementation of @Argument so we can instantiate it.
 * @author Jan Materne
 */
public class ArgumentImpl extends AnnotationImpl implements Argument {
	public ArgumentImpl(ConfigElement ce) throws ClassNotFoundException {
		super(Argument.class,ce);
	}
}