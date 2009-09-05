package org.kohsuke.args4j.spi;

import java.lang.annotation.Annotation;


/**
 * Base class for the @Option and @Argument implementation classes.
 * @author Jan Materne
 */
public abstract class AnnotationImpl {
	protected AnnotationImpl(ConfigElement ce) throws ClassNotFoundException {
		aliases = ce.aliases != null ? ce.aliases : new String[]{};
		if (ce.handler != null) {
			handler = (Class<? extends OptionHandler>) Class.forName(ce.handler);
		} else {
			handler = OptionHandler.class;
		}
		metaVar = ce.metavar != null ? ce.metavar : "";
		multiValued = ce.multiValued;
		required = ce.required;
		usage = ce.usage != null ? ce.usage : "";
	}
	public String[] aliases;
	public String[] aliases() {
		return aliases;
	}
	public Class<? extends OptionHandler> handler;
	public Class<? extends OptionHandler> handler() {
		return handler;
	}
	public String metaVar;
	public String metaVar() {
		return metaVar;
	}
	public boolean multiValued;
	public boolean multiValued() {
		return multiValued;
	}
	public boolean required;
	public boolean required() {
		return required;
	}
	public String usage;
	public String usage() {
		return usage;
	}
	public Class<? extends Annotation> annotationType;
	public Class<? extends Annotation> annotationType() {
		return annotationType;
	}
	public int index;
	public int index() {
		return index;
	}
}