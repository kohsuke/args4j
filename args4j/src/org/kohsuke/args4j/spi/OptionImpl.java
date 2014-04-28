package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Option;

/**
 * Implementation of @Option so we can instantiate it.
 * @author Jan Materne
 */
public class OptionImpl extends AnnotationImpl implements Option {
    public OptionImpl(ConfigElement ce) throws ClassNotFoundException {
        super(Option.class, ce);
        name = ce.name;
        property = ce.property;
    }

    public String name;
    public String name() {
        return name;
    }

    public String[] depends;
    public String[] depends() {
        return depends;
    }

    public boolean property;
    public boolean property() {
        return property;
    }
}