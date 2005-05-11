package org.kohsuke.args4j.apt;



/**
 * @author Kohsuke Kawaguchi
 */
interface AnnotationVisitor {
    void onOption( String name, String usage );
    void done();
}
