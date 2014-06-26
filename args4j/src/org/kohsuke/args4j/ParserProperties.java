package org.kohsuke.args4j;

/**
 *This class provides a new way to set various options for CmdLineParser, passing them in to the constructor.
 * Would have named it ParserOptions, but the term Option could be confusing in this context.
 * Property is only a bit less confusing.
 */
public class ParserProperties {

    private static final int DEFAULT_USAGE_WIDTH = 80;
    private static final boolean DEFAULT_SHOULD_SORT_OPTIONS = true;

    private int usageWidth = DEFAULT_USAGE_WIDTH;
    private boolean shouldSortOptions = DEFAULT_SHOULD_SORT_OPTIONS;

    private ParserProperties() {
    }

    public static ParserProperties defaults() {
        return new ParserProperties();
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
     * @param shouldSortOptions
     *      If true, options are sorted in alphabetical order for display purposes,
     *      while arguments are in defined order. If false, options are also shown
     *      in defined order.
     */
    public ParserProperties shouldSortOptions(boolean shouldSortOptions) {
        this.shouldSortOptions = shouldSortOptions;
        return this;
    }

    /**
     * @return
     *      True means options are sorted in alphabetical order for display purposes,
     *      while arguments are in defined order; false means options are also shown
     *      in defined order.
     */
    boolean shouldSortOptions() {
        return shouldSortOptions;
    }
}
