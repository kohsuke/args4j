package org.kohsuke.args4j.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.kohsuke.args4j.apt.Main;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.net.URLDecoder;
import java.util.List;

/**
 * Maven goal to create a usage description file.
 */
@Mojo(name = "usage")
public class Args4jUsageMojo extends AbstractMojo {

    /**
     * Mode. 'TXT' 'XML' or 'HTML'
     */
	@Parameter(defaultValue = "TXT", required = true)
    private String mode;

    /**
     * Location of the source files.
     */
	@Parameter(defaultValue = "${basedir}/src/main/java", required = true)
    private String sourceDir;

    /**
     * directory where the the usage are generated
     */
	@Parameter(defaultValue = "${project.build.directory}/args4j", required = true)
    protected String args4jBuildDirPath;

    /**
     * Source files to analyze.
     */
	@Parameter
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
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String enc = System.getProperty("file.encoding");
            path = URLDecoder.decode(path, enc);
            jar = new File(path);
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
        pb.redirectError(Redirect.INHERIT);
        pb.redirectOutput(Redirect.INHERIT);

        printCommand(pb);
        Process process = pb.start();

        int r = process.waitFor();
        if (r !=0)
            throw new IOException("args4j generation failed: "+r);
    }

    private void printCommand(ProcessBuilder pb) {
        StringBuilder sb = new StringBuilder();
        sb.append("Running: ");
        for (String s : pb.command()) {
            sb.append(s).append(" ");
        }
        sb.append("from ").append(dir(pb).getAbsolutePath());
        getLog().debug(sb.toString());
    }

    private File dir(ProcessBuilder pb) {
        File directory = pb.directory();
        if (directory == null) {
            return new File(System.getProperty("user.dir"));
        }
        return directory;
    }
}
