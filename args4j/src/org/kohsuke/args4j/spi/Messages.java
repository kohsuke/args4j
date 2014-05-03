package org.kohsuke.args4j.spi;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import org.kohsuke.args4j.MessageFormatter;

/**
 * @author Kohsuke Kawaguchi
 */
public enum Messages implements MessageFormatter {
    ILLEGAL_OPERAND,
    ILLEGAL_CHAR,
    ILLEGAL_BOOLEAN,
    ILLEGAL_METHOD_SIGNATURE,
    ILLEGAL_FIELD_SIGNATURE,
    ILLEGAL_LIST,
    FORMAT_ERROR_FOR_MAP,
    MAP_HAS_NO_KEY,
    ILLEGAL_IP_ADDRESS,
    ILLEGAL_MAC_ADDRESS,
    ILLEGAL_UUID
    ;

    private static ResourceBundle rb;
    
    public String formatWithLocale( Locale locale, Object... args ) {
        ResourceBundle localized = ResourceBundle.getBundle(Messages.class.getName(), locale);
        return MessageFormat.format(localized.getString(name()),args);
    }
    
    public String format( Object... args ) {
        synchronized(Messages.class) {
            if(rb==null)
                rb = ResourceBundle.getBundle(Messages.class.getName());
            return MessageFormat.format(rb.getString(name()),args);
        }
    }
}
