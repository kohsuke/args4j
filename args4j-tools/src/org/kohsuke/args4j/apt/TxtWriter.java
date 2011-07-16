package org.kohsuke.args4j.apt;

import com.sun.mirror.declaration.ClassDeclaration;
import org.kohsuke.args4j.Option;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Writes the option list as TXT to a {@link java.io.Writer}.
 *
 * @author Jerome Lacoste
 */
class TxtWriter implements AnnotationVisitor {
    private final PrintWriter out;

    public TxtWriter(Writer out, ClassDeclaration d) {
        this.out = new PrintWriter(out);
        this.out.println("Usage: " + d.getQualifiedName());
    }

    public void onOption(String name, String usage) {
        throw new UnsupportedOperationException("never used");
    }

    public void onOption( Option option, String usage ) {
        out.println("\t" + option.name() + ": " + required(option)  + usage);
    }

    private String required(Option option) {
        if (option.required()) {
            return "[required] ";
        }
        return "[optional] ";
    }

    public void done() {
        out.close();
    }
}
