package org.kohsuke.args4j.apt;

import java.util.*;

/**
 * Reorders the annotation according to their canonical name
 */
public class AnnotationVisitorReorderer implements AnnotationVisitor {
    private AnnotationVisitor target;

    private List<OptionWithUsage> arguments = new ArrayList<OptionWithUsage>();

    public AnnotationVisitorReorderer(AnnotationVisitor target) {
        this.target = target;
    }

    public void onOption(String name, String usage) {
        throw new UnsupportedOperationException("method not used");
    }

    public void onOption(OptionWithUsage optionWithUsage) {
        arguments.add(optionWithUsage);
    }

    public void done() {
        reorderArguments();
        for (OptionWithUsage argument : arguments) {
            target.onOption(argument);
        }
        target.done();
    }

    private void reorderArguments() {
        Collections.sort(arguments, new NaturalOrderOptionsComparator());
    }

    private static class NaturalOrderOptionsComparator implements Comparator<OptionWithUsage> {
        public int compare(OptionWithUsage o1, OptionWithUsage o2) {
            return o1.option.name().compareTo(o2.option.name());
        }
    }
}
