package org.kohsuke.args4j;

public class Inheritance extends InheritanceFather implements InheritanceMother {
    @Option(name="-m")
    public String me;

    public String mom;
    public void setMom(String m) {
        mom = m;
    }
    
    
}
