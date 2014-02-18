package org.kohsuke.args4j.spi;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Kohsuke Kawaguchi
 */
public enum Messages {
    ILLEGAL_OPERAND,
    ILLEGAL_CHAR,
    ILLEGAL_BOOLEAN,
    ILLEGAL_METHOD_SIGNATURE,
    ILLEGAL_FIELD_SIGNATURE,
    ILLEGAL_LIST,
    FORMAT_ERROR_FOR_MAP,
    MAP_HAS_NO_KEY,
    ILLEGAL_IP_ADDRESS,
    ILLEGAL_MAC_ADDRESS
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
