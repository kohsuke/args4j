package org.kohsuke.args4j;

/**
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public interface AntElementParser {
    /**
     * Returns the object that actually parses the nested element.
     * 
     * @return
     *      a non-null valid object.
     */
    Object getParser();
    
    /**
     * Once the object returned from {@link #getParser()} fully
     * parses the child element, this method is called to notify
     * the completion.
     * 
     * @param parser
     *      The object created from the {@link #getParser()} method.
     */
    void onComplete( Object parser );
}
