package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.OptionHandler;

/**
 * Run-time copy of the Option or Argument annotation. By definition, unnamed options
 * are arguments (and instances of this class). Named options are actually a subclass.
 * 
 * @author Mark Sinke
 */
public class OptionDef {
	private final String usage;
	private final String metaVar;
	private final boolean required;
	private final boolean multiValued;
	private final Class<? extends OptionHandler> handler;

	public OptionDef(Argument a, boolean forceMultiValued) {
		this(a.usage(), a.metaVar(), a.required(), a.handler(), a.multiValued() || forceMultiValued);
	}

	protected OptionDef(String usage, String metaVar, boolean required,
			Class<? extends OptionHandler> handler, boolean multiValued) {
		this.usage = usage;
		this.metaVar = metaVar;
		this.required = required;
		this.handler = handler;
		this.multiValued = multiValued;
	}

	public String usage() {
		return usage;
	}

	public String metaVar() {
		return metaVar;
	}

	public boolean required() {
		return required;
	}

	public Class<? extends OptionHandler> handler() {
		return handler;
	}

	public boolean isMultiValued() {
		return multiValued;
	}
	
	public boolean isArgument() {
		return true;
	}

	@Override
	public String toString() {
		return metaVar() != null ? metaVar() : "ARG";
	}
}
