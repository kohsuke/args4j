package org.kohsuke.args4j;

import org.kohsuke.args4j.Option;

public class SimpleString {
    @Option(name="-str",usage="set a string")
    public String str = "default";
}
