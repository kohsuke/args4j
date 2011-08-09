package org.kohsuke.args4j.apt;

import junit.framework.TestCase;
import org.kohsuke.args4j.Option;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnnotationVisitorReordererTest extends TestCase {

    private AnnotationVisitor target;
    private AnnotationVisitorReorderer reorderer;

    final OptionWithUsage optionb = new OptionWithUsage(mockOption("-b"), "optionb");
    final OptionWithUsage optiona = new OptionWithUsage(mockOption("-a"), "optiona");

    protected void setUp() throws Exception {
        super.setUp();
        target = mock(AnnotationVisitor.class);
        reorderer = new AnnotationVisitorReorderer(target);
    }

    public void testReorderWithUnorderedAnnotations() throws Exception {
        simulateOnOptionsThenDone(optionb, optiona);
        verifyOptionsThenDone(optiona, optionb);
    }

    public void testReorderWithAlreadyOrderedAnnotations() throws Exception {
        simulateOnOptionsThenDone(optiona, optionb);
        verifyOptionsThenDone(optiona, optionb);
    }

    private void simulateOnOptionsThenDone(OptionWithUsage... options) {
        for (OptionWithUsage option : options) {
            reorderer.onOption(option);
        }
        reorderer.done();
    }

    private void verifyOptionsThenDone(OptionWithUsage... options) {
        for (OptionWithUsage option : options) {
            verify(target).onOption(option);
        }
        verify(target).done();
    }

    private Option mockOption(String name) {
        Option option1 = mock(Option.class);
        when(option1.name()).thenReturn(name);
        return option1;
    }
}
