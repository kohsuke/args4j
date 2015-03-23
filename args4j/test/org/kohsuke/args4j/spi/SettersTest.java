package org.kohsuke.args4j.spi;

import java.lang.reflect.Field;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author dantuch
 */
@SuppressWarnings({"static-method", "unused"})
public class SettersTest extends TestCase {

    private final String finalField = "thisValueMakesItFinal";
    private String mutableField;

    public void testSNotCreateSetterForFinalField() throws Exception {
        // given
        Field f = SettersTest.class.getDeclaredField("finalField");
        // when
        try {
            Setters.create(f, null);
            fail();
        } catch (IllegalStateException e) {
            // expected
        }
    }

    public void testSCreateSetterForMutableField() throws Exception {
        // given
        Field f = SettersTest.class.getDeclaredField("mutableField");
        // when
        @SuppressWarnings("rawtypes")
        Setter created = Setters.create(f, null);
        // then
        Assert.assertNotNull(created);
    }
}
