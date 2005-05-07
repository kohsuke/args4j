package org.kohsuke.args4j;

/**
 * Common part of {@link Argument} and {@link CmdLineOption}.
 * 
 * <p>
 * <em>DO NOT</em> extend this interface directly for backward-compatibility.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public interface CmdLineItem {
    
    /**
     * Gets the name of this argument/option.
     * 
     * <p>
     * For options, the name should start with '-' (such as "-r" or "--verbose".)
     * For arguments, the name should not start with '-' (such as "name" or "output".)
     * 
     * <p>
     * This is used to build Ant task or to generate the usage screen.
     * 
     * @return
     *      the method must return a non-null valid {@link String}.
     */
    String getName();
    
    /**
     * Gets a description of this option/argumen
     * in a human readable plain text format.
     * 
     * @return
     *      null if the option/argument doesn't want to be listed
     *      in the usage screen.
     */
    String getDescription();
    
    /**
     * Called to parse an attribute specified in an Ant task.
     * 
     * @param antProject
     *      The task this {@link CmdLineItem} is parsing belongs to this
     *      {@link org.apache.tools.ant.Project} object. The signature
     *      is {@link Object} so that args4j can run without Ant in
     *      the classpath.
     * @param name
     *      Attribute name in the Ant build script.
     * @param value
     *      Attribute value.
     * 
     * @return
     *      true if the attribute is consumed successfuly.
     *      false if this option doesn't recognize the given attribute.
     * 
     * @throws CmdLineException
     *      If the attribute name is recognized but the value is
     *      unexpected. This exception will trigger the error reporting.
     */
    public boolean parseAntAttribute( CmdLineParser parser, Object antProject, String name, String value ) throws CmdLineException;
    
    /**
     * Called to parse a nested element
     * specified in an Ant task.
     * 
     * @param antProject
     *      The task this {@link CmdLineItem} is parsing belongs to this
     *      {@link org.apache.tools.ant.Project} object. The signature
     *      is {@link Object} so that args4j can run without Ant in
     *      the classpath.
     * 
     * @return
     *      null if the given element is not recognized by this
     *      {@link CmdLineOption}.
     */
    public AntElementParser createAntElementParser( CmdLineParser parser, Object antProject, String name ) throws CmdLineException;
}
