package org.kohsuke.args4j.apt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Annotation {@link Processor} to be invoked by javac.
 * 
 * This class receives options from the Main method through system properties
 * (ouch!).
 * 
 * @author Kohsuke Kawaguchi
 */
@SuppressWarnings("Since15")
public class AnnotationProcessorImpl extends AbstractProcessor {

    private File outDir;
    private String format;
    private Properties resource = null;

    public AnnotationProcessorImpl() {
        outDir = new File(System.getProperty("args4j.outdir"));
        format = System.getProperty("args4j.format");

        String res = System.getProperty("args4j.resource");
        if(res!=null && res.length()>0) {
            try {
                resource = new Properties();
                resource.load(new FileInputStream(res));
            } catch (IOException e) {
                throw new Error(e);
            }
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>(Arrays.asList(Option.class.getName(),
                Argument.class.getName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    private AnnotationVisitor createAnnotationVisitor(TypeElement te)
            throws IOException {
        FileWriter out = new FileWriter(new File(outDir, te.getQualifiedName()
                + "." + format.toLowerCase()));
        AnnotationVisitor writer;
        if(format.equals("XML"))
            writer = new XmlWriter(out, te);
        else if (format.equals("TXT"))
            writer = new TxtWriter(out, te);
        else
            writer = new HtmlWriter(out);
        return new AnnotationVisitorReorderer(writer);
    }

    private void scan(TypeElement decl, AnnotationVisitor visitor) {

        Types typeUtils = processingEnv.getTypeUtils();

        while (decl != null) {
            for (Element f : decl.getEnclosedElements()) {
                scan(f, visitor);
            }
            decl = (TypeElement) typeUtils.asElement(decl.getSuperclass());
        }

        visitor.done();
    }

    private void scan(Element f, AnnotationVisitor visitor) {
        Option o = f.getAnnotation(Option.class);
        if(o==null) return;

        String usage = getUsage(o);
        if(isOptionHidden(usage))    return;

        visitor.onOption(new OptionWithUsage(o, usage));
    }

    private boolean isOptionHidden(String usage) {
        return usage==null || usage.length()==0;
    }

    private String getUsage(Option o) {
        if(resource==null)
            return o.usage();
        else
            return resource.getProperty(o.usage());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {

        Set<? extends Element> params = roundEnv.getElementsAnnotatedWith(Option.class);

        final Set<TypeElement> optionBeans = new HashSet<TypeElement>();

        for (Element d : params) {

            d.accept(new SimpleElementVisitor6<Void, Void>() {
                @Override
                public Void visitVariable(VariableElement e, Void p) {
                    TypeElement dt = (TypeElement) e.getEnclosingElement();
                    optionBeans.add(dt);
                    return null;
                }

                public Void visitExecutable(ExecutableElement m, Void p) {
                    optionBeans.add((TypeElement) m.getEnclosingElement());
                    return null;
                }
            }, null);
        }

        for (TypeElement t : optionBeans) {
            // make sure that they are on classes
            if (t.getKind().isClass()) {
                try {
                    AnnotationVisitor writer = createAnnotationVisitor(t);
                    processingEnv.getMessager().printMessage(Kind.NOTE,
                            "Processing " + t.getQualifiedName());
                    scan(t, writer);
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Kind.ERROR,
                            e.getMessage());
                }
            } else {
                processingEnv.getMessager().printMessage(Kind.ERROR,
                        "args4j annotations need to be placed on a class", t);
            }
        }

        return true;
    }
}
