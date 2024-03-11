package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;

import java.lang.reflect.AnnotatedElement;

public class ArraySetter implements Setter {

    private final int index;
    private final Class type;
    private final Object[] values;

    public ArraySetter(Object[] values, int index, Class type) {
        this.index = index;
        this.type = type;
        this.values = values;
    }

    public void addValue(Object value) throws CmdLineException {
        Object old = values[index];
        values[index] = old == null ? value : old + " " + value;
    }

    public Class getType() {
        return type;
    }

    public boolean isMultiValued() {
        return getType().isArray();
    }

    public FieldSetter asFieldSetter() {
        return null;
    }

    public AnnotatedElement asAnnotatedElement() {
        return null;
    }
}
