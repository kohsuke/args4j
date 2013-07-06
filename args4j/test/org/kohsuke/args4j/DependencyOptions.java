package org.kohsuke.args4j;

/**
 * @author Nicolas Geraud
 */
public class DependencyOptions {
    @Option(name = "-z", depends ={"-y"})
    int a;

    @Option(name = "-y", depends ={"-z"})
    int b;

    @Option(name = "-a")
    int w;

    @Option(name = "-b")
    int x;

    @Option(name = "-c", depends ={"-a"})
    int y;

    @Option(name = "-d", depends ={"-b", "-c"})
    int z;
}
