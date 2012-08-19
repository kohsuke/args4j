package org.kohsuke.args4j;

/**
 * @author Nicolas Geraud
 */
public class RequiresOption {
    @Option(name = "-z", requires={"-y"})
    int a;

    @Option(name = "-y", requires={"-z"})
    int b;

    @Option(name = "-a")
    int w;

    @Option(name = "-b")
    int x;

    @Option(name = "-c", requires={"-a"})
    int y;

    @Option(name = "-d", requires={"-b", "-c"})
    int z;
}
