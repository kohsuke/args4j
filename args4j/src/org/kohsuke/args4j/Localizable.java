package org.kohsuke.args4j;

import java.util.Locale;

/**
 * A message that can be formatted with arguments and locale.
 *
 * @see Messages
 * @see org.kohsuke.args4j.spi.Messages
 * @author Stephan Fuhrmann
 */
public interface Localizable {
    public String formatWithLocale( Locale locale, Object... args );

    /**
     * Format with the default locale.
     */
    public String format( Object... args );
}
