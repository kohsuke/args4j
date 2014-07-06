package org.kohsuke.args4j.spi;

/**
 * The ConfigElement is an <tt>&lt;option&gt;</tt> or <tt>&lt;argument&gt;</tt> tag
 * in the xml configuration file.
 * @author Jan Materne
 */
public class ConfigElement {
	public String field;
	public String method;
	public String name;
	public String usage = "";
	public String handler;
	public String metavar = "";
	public String[] aliases = {};
	public boolean multiValued = false;
	public boolean required = false;
	public boolean hidden = false;
	/**
	 * Ensures that only a field XOR a method is set.
	 * @return
	 */
	public boolean isInvalid() {
		return field == null && method == null || field != null && method != null;
	}
}