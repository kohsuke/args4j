package org.kohsuke.args4j;

/**
 * Run-time copy of {@link Option} annotation.
 */
public final class NamedOptionDef extends OptionDef {
    private final String name;
	private final String[] aliases;
    private final String[] depends;
    
    /**
     * @deprecated
     *      multi-valuedness as option definition does not make sense. It's driven by the setter.
     */
    public NamedOptionDef(Option o, boolean forceMultiValued) {
    	this(o);
    }

    public NamedOptionDef(Option o) {
    	super(o.usage(),o.metaVar(),o.required(),o.help(),o.hidden(),o.handler(),false);

    	this.name = o.name();
    	this.aliases = o.aliases();
        this.depends = o.depends();
    }

    public String name() {
    	return name;
    }
    
    public String[] aliases() {
    	return aliases;
    }

    public String[] depends() {
        return depends;
    }
    
    @Override
    public String toString() {
    	if (aliases.length > 0) {
    		String str = "";
    		for (String alias : aliases) {
    			if (str.length() > 0) {
    				str += ", ";
    			}
    			str += alias;
    		}
    		return name() + " (" + str + ")";
    	}
    	return name();
    }
    
    @Override
    public boolean isArgument() {
    	return false;
    }
}
