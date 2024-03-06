package org.kohsuke.args4j.spi;

/**
 * The ConfigElement is an <code>&lt;option&gt;</code> or <code>&lt;argument&gt;</code> tag
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
	 * @return Whether this element has invalid settings.
	 */
	public boolean isInvalid() {
		return field == null && method == null || field != null && method != null;
	}
}
