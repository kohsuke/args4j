package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.OptionHandler;

import java.util.Comparator;

/**
 * Set of properties that controls {@link CmdLineParser} behaviours.
 *
 * @see CmdLineParser#CmdLineParser(Object, ParserProperties)
 */
public class ParserProperties {

    private static final int DEFAULT_USAGE_WIDTH = 80;

    private int usageWidth = DEFAULT_USAGE_WIDTH;
    private Comparator<OptionHandler> optionSorter = DEFAULT_COMPARATOR;
    private String optionValueDelimiter=" ";
    private boolean atSyntax = true;
    private boolean showDefaults = true;
    
    private ParserProperties() {
    }

    /**
     * Returns an instance of the default parser properties.
     * This instance can be manipulated with the {@code withXXX()} methods
     * in this class.
     */
    public static ParserProperties defaults() {
        return new ParserProperties();
    }

    /**
     * Toggles the parsing of @-prefixes in values.
     * If a command line value starts with @, it is interpreted
     * as being a file, loaded, and interpreted as if
     * the file content would have been passed to the command line.
     * @param atSyntax {@code true} if at sign is being parsed, {@code false}
     * if it is to be ignored. Defaults to {@code true}.
     * @see #getAtSyntax() 
     */
    public ParserProperties withAtSyntax(boolean atSyntax) {
        this.atSyntax = atSyntax;
        return this;
    }
    
    
    /**
     * Gets whether @-prefix-parsing is enabled.
     * @see #withAtSyntax(boolean) 
     */
    public boolean getAtSyntax() {
        return atSyntax;
    }

    /**
     * Toggles the showing of default values in the command line help.
     * @param showDefaults {@code true} if to show defaults, {@code false}
     * otherweise. Defaults to {@code true}.
     * @see #getShowDefaults() 
     */
    public ParserProperties withShowDefaults(boolean showDefaults) {
        this.showDefaults = showDefaults;
        return this;
    }    
    
    /**
     * Gets whether show defaults is enabled.
     * @see #withShowDefaults(boolean) 
     */
    public boolean getShowDefaults() {
        return showDefaults;
    }
    
    /**
     * Sets the width of a usage line.
     * If the usage message is longer than this value, the parser wraps the line.
     *
     * Defaults to {@code 80}.
     *
     * @param usageWidth the width of the usage output in columns.
     * @throws IllegalArgumentException if {@code usageWidth} is negative
     */
    public ParserProperties withUsageWidth(int usageWidth) {
        if (usageWidth < 0)
            throw new IllegalArgumentException("Usage width is negative");
        this.usageWidth = usageWidth;
        return this;
    }

    /**
     * @return the width of a usage line.
     */
    int getUsageWidth() {
        return usageWidth;
    }

    /**
     * Controls how options are sorted in the usage screen.
     *
     * @param sorter
     *      If non-{@code null}, options are sorted in the order induced by this comparator.
     */
    public ParserProperties withOptionSorter(Comparator<OptionHandler> sorter) {
        this.optionSorter = sorter;
        return this;
    }

    /**
     * @return
     *      {@code null} if options are left unsorted and should be listed by their discovery order.
     *      Otherwise the returned comparator is used to sort options.
     *      The default value is a comparator that sorts options alphabetically.
     */
    Comparator<OptionHandler> getOptionSorter() {
        return optionSorter;
    }

    /**
     * Sets the string used to separate option name and its value (such as --foo=bar vs --foo bar)
     *
     * Default to whitespace. Note that the tokens separated in the argument array (such as '-foo','bar')
     * is always recognized as a valid name/value separator.
     *
     */
    public ParserProperties withOptionValueDelimiter(String v) {
        this.optionValueDelimiter = v;
        return this;
    }

    public String getOptionValueDelimiter() {
        return this.optionValueDelimiter;
    }

    static final Comparator<OptionHandler> DEFAULT_COMPARATOR = new Comparator<OptionHandler>() {
        public int compare(OptionHandler o1, OptionHandler o2) {
            return o1.option.toString().compareTo(o2.option.toString());
        }
    };
}
