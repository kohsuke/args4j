package org.kohsuke.args4j;

/**
 * Associates a Java field/property with an element/attribute of an Ant task.
 *
 * @author Kohsuke Kawaguchi
 */
public @interface Ant {
    /**
     * Whether this option is mapped to an attribute or a nested element.
     */
    Kind type() default Kind.ATTRIBUTE;

    /**
     * Name of the attribute or the nested element.
     *
     * <p>
     * If left unspecified, the value is computed from {@link Option#name()}
     * on the same field/method by removing the leading '-'.
     */
    String name() default "";

    /**
     * The usage of this option.
     *
     * <p>
     * If left unspecified, the value is taken from {@link Option#usage()}
     * on the same field/method.
     */
    String usage() default "";

    enum Kind {
        /**
         * The option is mapped to a nested element of the Ant task.
         */
        ELEMENT,
        /**
         * The option is mapped to an attribute of the Ant task.
         */
        ATTRIBUTE,
    }
}
