package org.kohsuke.args4j.apt;

import javax.lang.model.element.TypeElement;
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

    public TxtWriter(Writer out, TypeElement d) {
        this.out = new PrintWriter(out);
        this.out.println("Usage: " + d.getQualifiedName());
    }

    public void onOption(String name, String usage) {
        throw new UnsupportedOperationException("never used");
    }

    public void onOption( OptionWithUsage optionWithUsage) {
        out.println("\t" + optionWithUsage.option.name() + ": " + required(optionWithUsage.option)  + optionWithUsage.usage);
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
