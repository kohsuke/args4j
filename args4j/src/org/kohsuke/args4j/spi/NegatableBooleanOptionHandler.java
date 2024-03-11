package org.kohsuke.args4j.spi;

import java.util.Arrays;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.IllegalAnnotationError;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * Boolean {@link OptionHandler} that allows values to be specified as
 * --<option> and negated with --no<option>.
 *
 * Implicitly adds the negated version of the option to the list of aliases
 * unless the user has already specified it.
 *
 * @Author Robin Garner
 */
public class NegatableBooleanOptionHandler extends OptionHandler<Boolean> {

    private final String negatedName;

    public NegatableBooleanOptionHandler(CmdLineParser parser,
	    OptionDef option, Setter<? super Boolean> setter) {
	super(parser, addNegatedName(option), setter);

	this.negatedName = negatedOption(((NamedOptionDef) option).name());
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
	if (params.getParameter(-1).equals(negatedName)) {
	    setter.addValue(false);
	} else {
	    setter.addValue(true);
	}
	return 0;
    }

    @Override
    public String getDefaultMetaVariable() {
	return null;
    }

    /**
     * Return a new OptionDef that includes the negated name of the option in its
     * aliases.  If the user has already done this, return the original untouched.
     * @param option Original option
     * @return The option, with the negated name guaranteed to be in the aliases.
     * @throws IllegalAnnotationError if option is not a NanedOptionDef
     */
    private static OptionDef addNegatedName(final OptionDef option) {
	if (!(option instanceof NamedOptionDef)) {
	    throw new IllegalAnnotationError("NegatableBooleanOptionHandler can only be used for named options.");
	}
	if (hasNegatedAlias((NamedOptionDef)option)) {
	    return option;
	}
	return new NamedOptionDef((NamedOptionDef)option) {

	    @Override
	    public String[] aliases() {
		String[] aliases = super.aliases();
		String[] result = Arrays.copyOf(aliases, aliases.length+1);
		result[aliases.length] = negatedOption(name());
		return result;
	    }

	};
    }

    private static String negatedOption(String name) {
	return name.replaceFirst("(-*)", "$1no");
    }

    private static boolean hasNegatedAlias(NamedOptionDef option) {
	return find(negatedOption(option.name()), option.aliases());
    }

    /**
     * Look for a string in an array of strings
     * @param str String to look for
     * @param strings Array of strings to search
     * @return {@code true} if {@code str} is present in {@code strings}
     */
    private static boolean find(String str, String[] strings) {
	for (String alias : strings) {
	    if (alias.equals(str)) {
		return true;
	    }
	    System.err.println(alias + " != " + str);
	}
	return false;
    }

}
