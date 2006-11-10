package org.kohsuke.args4j;

/**
 * Run-time copy of the Option or Argument annotation.
 */
public final class NamedOptionDef extends OptionDef {
    private final String name;
	private final String[] aliases;
    
    public NamedOptionDef(Option o, boolean forceMultiValued) {
    	super(o.usage(),o.metaVar(),o.required(),o.handler(),o.multiValued() || forceMultiValued);
    	
    	this.name = o.name();
    	this.aliases = o.aliases();
    }
    
    public String name() {
    	return name;
    }
    
    public String[] aliases() {
    	return aliases;
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
