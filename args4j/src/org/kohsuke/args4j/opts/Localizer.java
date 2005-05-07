package org.kohsuke.args4j.opts;

import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class Localizer {
    /** Format string that includes resource expansions. */
    private final String format;
    
    /**
     * Remembers the caller stack in case 
     */
    private final StackTraceElement[] callerStack;
    
    /**
     * Creates new localizer.
     * 
     * <p>
     * To improve the memory usage,
     * this method may return the string passed as the format parameter
     * (if no expansion is necessary) or it may return a {@link Localizer}
     * object.
     * 
     * @return
     *      Its {@link Object#toString()} method should be
     *      used to obtain the actual expanded string.
     */
    public static Object create(String format) {
        if( format.indexOf('$')<0 )
            return format;
        else
            return new Localizer(format);
    }
    
    private Localizer( String format ) {
        this.format = format;
        
        // the format string may contain resource expansions,
        // so remember the caller stack
        Exception e = new Exception();
        e.fillInStackTrace();
        callerStack = e.getStackTrace();
    }
    
    /**
     * Formats the string and returns it.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(format.length());
        
        int i=0;
        while(i<format.length()) {
            char ch = format.charAt(i++);
            if(ch!='$') {
                buf.append(ch);
                continue;
            }
            
            char next = format.charAt(i++);
            if( next=='$' ) {
                // replace $$ by $
                buf.append(ch);
                i++;
                continue;
            }
            if( next=='{' ) {
                int idx = format.indexOf('}',i);
                if(idx>=0) {
                    String key = format.substring(i,idx);
                    buf.append(expand(key));
                    i = idx+1;
                    continue;
                }
            }
            // unknown
            buf.append(ch);
            buf.append(next);
        }
        
        return buf.toString();
    }
    
    /**
     * Looks up resources and try to find the expansion.
     */
    private String expand( String key ) {
        Set namesTried = new HashSet();
        
        for( int i=0; i<callerStack.length; i++ ) {
            String className = callerStack[i].getClassName();
            
            // try with the class name first
            if( namesTried.add(className) ) {
                try {
                    ResourceBundle bundle = ResourceBundle.getBundle(className);
                    return bundle.getString(key);
                } catch( MissingResourceException e ) {} // continue
            }
            
            // then try <package>/Messages.properties
            int idx = className.lastIndexOf('.');
            String packageName;
            if(idx<0)   packageName="Messages";
            else        packageName=className.substring(0,idx)+".Messages";
            
            if( namesTried.add(packageName) ) {
                try {
                    ResourceBundle bundle = ResourceBundle.getBundle(packageName);
                    return bundle.getString(key);
                } catch( MissingResourceException e ) {} // continue
            }
        }
        
        throw new IllegalArgumentException("unable to find the resource for "+key);
    }
}
