package org.kohsuke.args4j;

/**
 *This class provides a new way to set various options for CmdLineParser, passing them in to the constructor.
 * Would have named it ParserOptions, but the term Option could be confusing in this context.
 * Property is only a bit less confusing.
 */
public class ParserProperties {

    private static final int DEFAULT_USAGE_WIDTH = 80;
    private static final boolean DEFAULT_SHOULD_SORT_OPTIONS = true;

    private final int usageWidth;
    private final boolean shouldSortOptions;

    private ParserProperties(int usageWidth, boolean sortOptions) {
        this.usageWidth = usageWidth;
        this.shouldSortOptions = sortOptions;
    }

    public static ParserProperties defaults() {
        return new ParserProperties(DEFAULT_USAGE_WIDTH, DEFAULT_SHOULD_SORT_OPTIONS);
    }

    /**
     *  Sets the width of a usage line.
     *  If the usage message is longer than this value, the parser wraps the line.
     *
     *  Defaults to {@code 80}.
     *
     * @param usageWidth the width of the usage output in columns.
     * @throws IllegalArgumentException if {@code usageWidth} is negative
     */
    public ParserProperties withUsageWidth(int usageWidth) {
        if (usageWidth < 0)
            throw new IllegalArgumentException("Usage width is negative");
        if (this.usageWidth == usageWidth)
            return this;
        return new ParserProperties(usageWidth, this.shouldSortOptions);
    }

    /**
     * @return the width of a usage line.
     */
    public int getUsageWidth() {
        return usageWidth;
    }

    /**
     * @param shouldSortOptions
     *      If true, options are sorted in alphabetical order for display purposes,
     *      while arguments are in defined order. If false, options are also shown
     *      in defined order.
     */
    public ParserProperties shouldSortOptions(boolean shouldSortOptions) {
        if (this.shouldSortOptions == shouldSortOptions)
            return this;
        return new ParserProperties(this.usageWidth, shouldSortOptions);
    }

    /**
     * @return
     *      True means options are sorted in alphabetical order for display purposes,
     *      while arguments are in defined order; false means options are also shown
     *      in defined order.
     */
    public boolean shouldSortOptions() {
        return shouldSortOptions;
    }
}
