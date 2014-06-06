package org.kohsuke.args4j;

/**
 *This class provides a new way to set various options for CmdLineParser, passing them in to the constructor.
 * Would have named it ParserOptions, but the term Option could be confusing in this context.
 * Property is only a bit less confusing.
 */
public class ParserProperties {

    private final int usageWidth;
    private final boolean sortOptions;

    private ParserProperties(int usageWidth, boolean sortOptions) {
        this.usageWidth = usageWidth;
        this.sortOptions = sortOptions;
    }

    public static ParserProperties defaults() {
        return new ParserProperties(80, true);
    }

    /**
     * Sets the width of the usage output.
     * @param usageWidth the width of the usage output in columns.
     * @throws IllegalArgumentException if {@code usageWidth} is negative
     */
    public ParserProperties withUsageWidth(int usageWidth) {
        if (usageWidth < 0)
            throw new IllegalArgumentException("Usage width is negative");
        if (this.usageWidth == usageWidth)
            return this;
        return new ParserProperties(usageWidth, this.sortOptions);
    }

    /**
     * @return the width of the usage output in columns.
     */
    public int getUsageWidth() {
        return usageWidth;
    }

    /**
     * @param sortOptions
     *      If true, options are sorted in alphabetical order for display purposes,
     *      while arguments are in defined order. If false, options are also shown
     *      in defined order.
     */
    public ParserProperties shouldSortOptions(boolean sortOptions) {
        if (this.sortOptions == sortOptions)
            return this;
        return new ParserProperties(this.usageWidth, sortOptions);
    }

    /**
     * @return
     *      True means options are sorted in alphabetical order for display purposes,
     *      while arguments are in defined order; false means options are also shown
     *      in defined order.
     */
    public boolean shouldSortOptions() {
        return sortOptions;
    }
}
