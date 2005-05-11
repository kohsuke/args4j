package org.kohsuke.args4j.apt;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.util.SimpleDeclarationVisitor;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link AnnotationProcessorFactory} to be invoked by APT.
 *
 * This class receives options from the Main method through system properties (ouch!).
 *
 * @author Kohsuke Kawaguchi
 */
public class AnnotationProcessorFactoryImpl implements AnnotationProcessorFactory {

    private File outDir;
    private String format;

    public AnnotationProcessorFactoryImpl() {
        outDir = new File(System.getProperty("args4j.outdir"));
        format = System.getProperty("args4j.format");
    }

    public Collection<String> supportedOptions() {
        return Collections.emptyList();
    }

    public Collection<String> supportedAnnotationTypes() {
        return Arrays.asList(Option.class.getName(),Argument.class.getName());
    }

    public AnnotationProcessor getProcessorFor(final Set<AnnotationTypeDeclaration> annotationTypeDeclarations, final AnnotationProcessorEnvironment env) {
        return new AnnotationProcessor() {
            public void process() {
                Collection<Declaration> params = env.getDeclarationsAnnotatedWith((AnnotationTypeDeclaration)env.getTypeDeclaration(Option.class.getName()));

                final Set<TypeDeclaration> optionBeans = new HashSet<TypeDeclaration>();
                for (Declaration d : params) {
                    d.accept(new SimpleDeclarationVisitor() {
                        public void visitFieldDeclaration(FieldDeclaration f) {
                            TypeDeclaration dt = f.getDeclaringType();
                            optionBeans.add(dt);
                        }

                        public void visitMethodDeclaration(MethodDeclaration m) {
                            optionBeans.add(m.getDeclaringType());
                        }
                    });
                }

                // make sure that they are on classes
                for (TypeDeclaration t : optionBeans) {
                    if(t instanceof ClassDeclaration) {
                        ClassDeclaration cd = (ClassDeclaration)t;
                        try {
                            FileWriter out = new FileWriter(new File(outDir,cd.getQualifiedName()+"."+format.toLowerCase()));
                            AnnotationVisitor writer;
                            if(format.equals("XML"))
                                writer = new XmlWriter(out,cd);
                            else
                                writer = new HtmlWriter(out);
                            env.getMessager().printNotice("Processing "+cd.getQualifiedName());
                            scan(cd, writer);
                        } catch (IOException e) {
                            env.getMessager().printError(e.getMessage());
                        }
                    } else {
                        env.getMessager().printError(t.getPosition(),
                            "args4j annotations need to be placed on a class");
                    }
                }
            }
        };
    }

    private void scan(ClassDeclaration decl, AnnotationVisitor visitor) {
        while(decl!=null) {
            for( FieldDeclaration f : decl.getFields() ) {
                Option o = f.getAnnotation(Option.class);
                if(o!=null)
                    visitor.onOption(o);
            }

            for (MethodDeclaration m : decl.getMethods()) {
                Option o = m.getAnnotation(Option.class);
                if(o!=null)
                    visitor.onOption(o);
            }

            ClassType sc = decl.getSuperclass();
            if(sc==null)    break;

            decl = sc.getDeclaration();
        }

        visitor.done();
    }

}
