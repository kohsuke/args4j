package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Argument;

/**
 * Implementation of @Argument so we can instantiate it.
 * @author Jan Materne
 */
public class ArgumentImpl extends AnnotationImpl implements Argument {

	public ArgumentImpl() throws ClassNotFoundException {
		this(new ConfigElement());
	}

	public ArgumentImpl(ConfigElement ce) throws ClassNotFoundException {
		super(Argument.class,ce);
	}

	public static ArgumentImpl copyOf(Argument toClone) throws ClassNotFoundException {
		if (toClone == null) {
			return null;
		}
		ConfigElement configElement = new ConfigElement();
		Class<? extends OptionHandler> handler = toClone.handler();
		configElement.handler = handler == null ? null : handler.getName();
		configElement.usage = toClone.usage();
		configElement.metavar = toClone.metaVar();
		configElement.multiValued = toClone.multiValued();
		configElement.required = toClone.required();
		configElement.hidden = toClone.hidden();
		return new ArgumentImpl(configElement);
	}
}
