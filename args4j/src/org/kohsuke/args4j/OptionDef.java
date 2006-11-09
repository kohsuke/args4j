package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.OptionHandler;

/**
 * Run-time copy of the Option or Argument annotation.
 */
public final class OptionDef {
	/** option name, or <code>""</code> for an argument definition */
    private final String name;
    private final String usage;
    private final String metaVar;
    private final boolean required;
    private final boolean multiValued;
    private final Class<? extends OptionHandler> handler;
    
    public OptionDef(Option o, boolean forceMultiValued) {
    	this(o.name(),o.usage(),o.metaVar(),o.required(),o.handler(),o.multiValued() || forceMultiValued);
    }

    public OptionDef(Argument a, boolean forceMultiValued) {
    	this("",a.usage(),a.metaVar(),a.required(),a.handler(),a.multiValued() || forceMultiValued);
    }

    private OptionDef(String name, String usage, String metaVar, boolean required, Class<? extends OptionHandler> handler, boolean multiValued) {
		this.name = name;
		this.usage = usage;
		this.metaVar = metaVar;
		this.required = required;
		this.handler = handler;
		this.multiValued = multiValued;
    }
    
    public String name() {
    	return name;
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
		return name.length() == 0; 
	}
}
