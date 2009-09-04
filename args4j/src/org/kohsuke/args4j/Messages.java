package org.kohsuke.args4j;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Kohsuke Kawaguchi
 */
enum Messages {
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
    FORMAT_ERROR_FOR_MAP,
    MAP_HAS_NO_KEY
    ;

    private static ResourceBundle rb;

    public String format( Object... args ) {
        synchronized(Messages.class) {
            if(rb==null)
                rb = ResourceBundle.getBundle(Messages.class.getName());
            return MessageFormat.format(rb.getString(name()),args);
        }
    }
}
