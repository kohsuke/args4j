package org.kohsuke.args4j.spi;

import java.lang.annotation.Annotation;
import java.util.Arrays;


/**
 * Base class for the @Option and @Argument implementation classes.
 * @author Jan Materne
 */
public abstract class AnnotationImpl implements Annotation {
    private final Class<? extends Annotation> annotationType;

    protected AnnotationImpl(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    protected AnnotationImpl(Class<? extends Annotation> annotationType, ConfigElement ce) throws ClassNotFoundException {
        this(annotationType);

		aliases = ce.aliases != null ? ce.aliases : new String[0];
		if (ce.handler != null) {
			handler = (Class<? extends OptionHandler>) Class.forName(ce.handler);
		} else {
			handler = OptionHandler.class;
		}
		metaVar = ce.metavar != null ? ce.metavar : "";
		multiValued = ce.multiValued;
		required = ce.required;
		hidden = ce.hidden;
		usage = ce.usage != null ? ce.usage : "";
	}

	public String[] aliases;
	public String[] aliases() {
		return Arrays.copyOf(aliases, aliases.length);
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
	public boolean help;
	public boolean help() {
		return help;
	}    
	public boolean hidden;
	public boolean hidden() {
		return hidden;
	}
	public String usage;
	public String usage() {
		return usage;
	}
	public Class<? extends Annotation> annotationType() {
		return annotationType;
	}
	public int index;
	public int index() {
		return index;
	}
}