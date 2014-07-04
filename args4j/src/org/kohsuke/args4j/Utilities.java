package org.kohsuke.args4j;

/**
 * Misc utility methods. Don't make this
 * class visible to the outside world.
 * When we switch to JDK 1.7, re-check the sense
 * of this class.
 */
class Utilities {
    
    private Utilities() {
        // no instance
    }
    
    /** This method is similar to {@code Objects.requireNonNull()}.
     * But this one is available for JDK 1.6 which is the
     * current target of args4j.
     * I didn't want to break compatibility with JDK 1.6.
     * @param obj the object to check for {@code null} value.
     * @param name the object name. If {@code obj} is {@code null}, then
     * an exception is constructed from this name.
     */
    static void checkNonNull(Object obj, String name) {
        if (obj == null) {
            throw new NullPointerException(name+" is null");
        }
    }    
}
