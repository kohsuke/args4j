package org.kohsuke.args4j.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.kohsuke.args4j.apt.Main;

import java.io.*;
import java.util.List;
/**
 * @goal usage
 */
public class Args4jMojo extends AbstractMojo {

    /**
     * Mode. 'TXT' 'XML' or 'HTML'
     * @parameter expression="TXT"
     * @required
     */
    private String mode;

    /**
     * Location of the source files.
     * @parameter expression="${basedir}/src/main/java"
     * @required
     */
    private String sourceDir;

    /**
     * directory where the the usage are generated
     * @parameter expression="${project.build.directory}/args4j"
     * @required
     */
    protected String args4jBuildDirPath;

    /**
     * @parameter
     */
    private List<String> sourceFiles;

    private File jar;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (sourceFiles == null || sourceFiles.size() == 0) {
            getLog().info("No sourceFiles defined. Skipping");
            return;
        }

        File args4jBuildDir = new File(args4jBuildDirPath);
        if (!args4jBuildDir.exists() && !args4jBuildDir.mkdirs()) {
            throw new MojoExecutionException("Couldn't create the directory " + args4jBuildDir.getAbsolutePath());
        }
        try {
            jar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        } catch (Exception e) {
            throw new MojoExecutionException("Couldn't find the jar of the arg4s tools");
        }

        getLog().debug("Jar path:" + jar);

        for (String relativeSourceFilePath : sourceFiles) {
            try {

                File sourceFile = new File(sourceDir, relativeSourceFilePath);
                if (!sourceFile.exists()) {
                    getLog().warn("Source file " + sourceFile.getAbsolutePath() + " not found. Skipping");
                    continue;
                }

                getLog().debug("Generating usage for " + sourceFile.getAbsolutePath());
                generateUsage(sourceFile.getAbsolutePath(), args4jBuildDir);
            } catch (Exception e) {
                throw new MojoExecutionException("Failed to generate usage for " + relativeSourceFilePath, e);
            }
        }
    }

    private void generateUsage(String sourceFile, File args4jBuildDir) throws InterruptedException, IOException {
        String outputDir = args4jBuildDir.getAbsolutePath();
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("java","-jar", jar.getAbsolutePath(), "-o", outputDir, "-mode", mode, sourceFile);

        printCommand(pb);
        Process process = pb.start();

        InputStreamReader isr = dumpOutput(process);
        int r = process.waitFor();

        isr.close();
        if (r !=0)
            throw new IOException("args4j generation failed: "+r);
    }

    private void printCommand(ProcessBuilder pb) {
        System.out.print("Running: ");
        for (String s : pb.command()) {
            System.out.print(s + " ");
        }
        System.out.print("from " + dir(pb).getAbsolutePath());
        System.out.println();
    }

    private File dir(ProcessBuilder pb) {
        File directory = pb.directory();
        if (directory == null) {
            return new File(System.getProperty("user.dir"));
        }
        return directory;
    }

    private InputStreamReader dumpOutput(Process process) throws IOException {
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
        return isr;
    }
}