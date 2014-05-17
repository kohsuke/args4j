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
    ILLEGAL_PATH,
    DEFAULT_META_EXPLICIT_BOOLEAN_OPTION_HANDLER,
    DEFAULT_META_FILE_OPTION_HANDLER,
    DEFAULT_META_INET_ADDRESS_OPTION_HANDLER,
    DEFAULT_META_MAC_ADDRESS_OPTION_HANDLER,
    DEFAULT_META_PATH_OPTION_HANDLER,
    DEFAULT_META_PATTERN_OPTION_HANDLER,
    DEFAULT_META_REST_OF_ARGUMENTS_HANDLER,
    DEFAULT_META_STRING_ARRAY_OPTION_HANDLER,
    DEFAULT_META_STRING_OPTION_HANDLER,
    DEFAULT_META_SUB_COMMAND_HANDLER,
    DEFAULT_META_URI_OPTION_HANDLER,
    DEFAULT_META_URL_OPTION_HANDLER,
    DEFAULT_META_UUID_OPTION_HANDLER
    ;

    public String formatWithLocale( Locale locale, Object... args ) {
        ResourceBundle localized = ResourceBundle.getBundle(Messages.class.getName(), locale);
        return MessageFormat.format(localized.getString(name()),args);
    }
    

    public String format( Object... args ) {
        return formatWithLocale(Locale.getDefault(),args);
    }
}
