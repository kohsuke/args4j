package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Option;

/**
 * Implementation of @Option so we can instantiate it.
 * @author Jan Materne
 */
public class OptionImpl extends AnnotationImpl implements Option {
	public OptionImpl(ConfigElement ce) throws ClassNotFoundException {
		super(Option.class,ce);
		name = ce.name;
	}
	public String name;
	public String name() {
		return name;
	}
}