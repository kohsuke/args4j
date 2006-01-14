package org.kohsuke.args4j;

public class Setter {

    public String str = "default";

    @Option(name="-str",usage="set a string")
    public void setStr(String str) {
        this.str = str.toUpperCase();
    }

}
