package org.kohsuke.args4j;

import java.util.Locale;

/**
 * @author Stephan Fuhrmann
 */
public interface MessageFormatter {
    
    public String formatWithLocale( Locale locale, Object... args );
    public String format( Object... args );
}
