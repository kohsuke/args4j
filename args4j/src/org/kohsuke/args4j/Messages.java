package org.kohsuke.args4j;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Kohsuke Kawaguchi
 */
enum Messages implements MessageFormatter {
    MISSING_OPERAND,
    UNDEFINED_OPTION,
    NO_ARGUMENT_ALLOWED,
    REQUIRED_OPTION_MISSING,
    TOO_MANY_ARGUMENTS,
    REQUIRED_ARGUMENT_MISSING,
    METADATA_ERROR,
    MULTIPLE_USE_OF_ARGUMENT,
    MULTIPLE_USE_OF_OPTION,
    UNKNOWN_HANDLER,
    NO_OPTIONHANDLER,
    NO_CONSTRUCTOR_ON_HANDLER,
    REQUIRES_OPTION_MISSING
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
