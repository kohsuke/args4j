package org.kohsuke.args4j;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * A message that can be formatted with arguments and locale.
 * <p>
 * The message is implicitly given by {@code this} object and can
 * contain formatting similar to the {@link MessageFormat} class.
 *
 * @see Messages
 * @see org.kohsuke.args4j.spi.Messages
 * @author Stephan Fuhrmann
 */
public interface Localizable {
    
    /**
     * Format the implicitly given message by {@code this} object with the given locale.
     * @param locale the locale to use for formatting .
     * @param args the arguments to use for formatting. See {@link MessageFormat#format(java.lang.String, java.lang.Object...)}.
     * @return the formatted string.
     */
    public String formatWithLocale( Locale locale, Object... args );

    /**
     * Format the implicitly given message by {@code this} object with the default locale.
     * @param args the arguments to use for formatting. See {@link MessageFormat#format(java.lang.String, java.lang.Object...)}.
     * @return the formatted string.
     */
    public String format( Object... args );
}
