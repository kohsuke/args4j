package org.kohsuke.args4j.apt;

import com.sun.mirror.declaration.ClassDeclaration;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Writes the option list as XML to a {@link Writer}.
 *
 * @author Kohsuke Kawaguchi
 */
class XmlWriter implements AnnotationVisitor {
    private final PrintWriter out;

    public XmlWriter(Writer out, ClassDeclaration d) {
        this.out = new PrintWriter(out);
        this.out.println("<usage class=\'"+d.getQualifiedName()+"\'>");
    }

    public void onOption( String name, String usage ) {
        out.println("  <option>");
        writeTag("name",name);
        writeTag("usage",usage);
        out.println("  </option>");
    }

    public void onOption( OptionWithUsage optionWithUsage ) {
        out.println("  <option>");
        writeTag("name",optionWithUsage.option.name());
        writeTag("usage",optionWithUsage.usage);
        out.println("  </option>");
    }

    private void writeTag(String tag, String value) {
        out.println("    <"+tag+"><![CDATA["+value+"]]></"+tag+">");
    }

    public void done() {
        out.println("</usage>");
        out.close();
    }
}
