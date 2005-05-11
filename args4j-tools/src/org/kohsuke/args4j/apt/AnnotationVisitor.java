package org.kohsuke.args4j.apt;

import org.kohsuke.args4j.Option;

/**
 * @author Kohsuke Kawaguchi
 */
interface AnnotationVisitor {
    void onOption( Option a );
    void done();
}
