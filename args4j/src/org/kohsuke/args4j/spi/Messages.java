package org.kohsuke.args4j.spi;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.kohsuke.args4j.Localizable;

/**
 * @author Kohsuke Kawaguchi
 */
public enum Messages implements Localizable {
    ILLEGAL_OPERAND,
    ILLEGAL_CHAR,
    ILLEGAL_BOOLEAN,
    ILLEGAL_METHOD_SIGNATURE,
    ILLEGAL_FIELD_SIGNATURE,
    ILLEGAL_LIST,
    FORMAT_ERROR_FOR_MAP,
    MAP_HAS_NO_KEY,
    ILLEGAL_IP_ADDRESS,
    ILLEGAL_PATTERN,
    ILLEGAL_MAC_ADDRESS,
    ILLEGAL_UUID,
    ILLEGAL_PATH
    ;

    public String formatWithLocale( Locale locale, Object... args ) {
        ResourceBundle localized = ResourceBundle.getBundle(Messages.class.getName(), locale);
        return MessageFormat.format(localized.getString(name()),args);
    }
    

    public String format( Object... args ) {
        return formatWithLocale(Locale.getDefault(),args);
    }
}
