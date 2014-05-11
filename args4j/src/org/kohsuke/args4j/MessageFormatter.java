package org.kohsuke.args4j;

import java.util.Locale;

/**
 * A formatter for localized messages.
 * @see Messages
 * @see org.kohsuke.args4j.spi.Messages
 * @author Stephan Fuhrmann
 */
public interface MessageFormatter {
    
    public String formatWithLocale( Locale locale, Object... args );
    public String format( Object... args );
}
