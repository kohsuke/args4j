package org.kohsuke.args4j;

import java.util.Arrays;

/**
 * Immutable run-time copy of {@link Option} annotation.
 */
public final class NamedOptionDef extends OptionDef {
    private final String name;
	private final String[] aliases;
    private final String[] depends;
    private final String[] forbids;
    
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
        this.forbids = o.forbids();
    }

    public String name() {
    	return name;
    }
    
    public String[] aliases() {
        if (aliases == null)
            return null;
    	return Arrays.copyOf(aliases, aliases.length);
    }

    public String[] depends() {
        if (depends == null)
            return null;
        return Arrays.copyOf(depends, depends.length);
    }

    public String[] forbids() {
        if (forbids == null)
            return null;
        return Arrays.copyOf(forbids, forbids.length);
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
