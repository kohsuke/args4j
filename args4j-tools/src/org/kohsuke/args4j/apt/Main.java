package org.kohsuke.args4j.apt;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;

import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.net.MalformedURLException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point that invokes APT.
 * @author Kohsuke Kawaguchi
 */
public class Main {

    @Option(name="-o",usage="output directory to place generated HTML/XML")
    private File outDir = new File(".");

    @Option(name="-mode",usage="output format. 'XML' or 'HTML'")
    private Mode mode = Mode.HTML;

    @Argument
    private List<String> sourceFiles = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        System.exit(new Main().run(args));
    }

    public int run(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("argsj-tools [options...] sourcefiles...");
            System.err.println("  Generates the list of options in XML/HTML");
            parser.printUsage(System.err);
            return -1;
        }

        // we'll use a separate class loader to reload our classes,
        // so parameters need to be set as system properties. Ouch!
        System.setProperty("args4j.outdir",outDir.getPath());
        System.setProperty("args4j.format",mode.name());

        // locate tools.jar
        ClassLoader cl = loadToolsJar();
        Class<?> apt = cl.loadClass("com.sun.tools.apt.Main");
        Method main = getProcessMethod(apt);
        return (Integer)main.invoke(null,new Object[]{
            cl.loadClass("org.kohsuke.args4j.apt.AnnotationProcessorFactoryImpl").newInstance(),
            sourceFiles.toArray(new String[0])});
    }

    private Method getProcessMethod(Class<?> apt) {
        for(Method m : apt.getDeclaredMethods()) {
            if(!m.getName().equals("process"))
                continue;

            Class<?>[] p = m.getParameterTypes();
            if(p.length!=2) continue;

            if(p[1]!=String[].class)    continue;
            if(!p[0].getName().endsWith("AnnotationProcessorFactory"))  continue;

            return m;
        }
        throw new Error("Unable to find the entry point to APT. Please use the latest version of JDK 5.0");
    }

    public ClassLoader loadToolsJar() {
        File jreHome = new File(System.getProperty("java.home"));
        File toolsJar = new File(jreHome.getParent(), "lib/tools.jar" );

        try {
            return new ReloadingClassLoader(new URLClassLoader(
                    new URL[]{ toolsJar.toURL() }, new IsolatingClassLoader(Main.class.getClassLoader())) );
        } catch (MalformedURLException e) {
            throw new Error(e);
        }
    }
}
