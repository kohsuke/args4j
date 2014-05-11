package org.kohsuke.args4j;

/**
 * @author Nicolas Geraud
 */
public class DependencyOptions {
    @Option(name = "-z", depends ={"-y"})
    int a;

    @Option(name = "-y", depends ={"-z"})
    int b;

    @Option(name = "-a", aliases="--alpha")
    int w;

    @Option(name = "-b", aliases="--bravo")
    int x;

    @Option(name = "-c", depends ={"--alpha"})
    int y;

    @Option(name = "-d", depends ={"-b", "-c"})
    int z;
    
    @Option(name = "-h", forbids ={"-a", "-b"})
    int o;
}
