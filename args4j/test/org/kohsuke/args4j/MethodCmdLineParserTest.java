package org.kohsuke.args4j;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MethodCmdLineParserTest {

    public static String method(
            String a1, // Implicit @Argument
            @Argument(usage = "location. Must exist") File a2,
            @Argument(usage = "number. Must be 2 to 7") Integer a3,
            @Argument(usage = "optional via @Nullable") @Nullable String a4,
            @Option(name = "-f", usage = "a (boolean) flag") Boolean flag,
            @Option(name = "-v", usage = "omitted optional") String omitted) {
        String values = toStringWithType(a1) +
                toStringWithType(a2) +
                toStringWithType(a3) +
                toStringWithType(flag);
        Assert.assertTrue(values, flag);
        Assert.assertNotNull(values, a1);
        Assert.assertNotNull(values, a2);
        Assert.assertNull(values, a4);
        return values;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Nullable {}

    private static class ClassWithMain {
        private static boolean mainInvoked = false;

        public static void typesafeMain(File arg1, int arg2, String arg3) {
            mainInvoked = true;
            Assert.assertNotNull(arg1);
            Assert.assertNotEquals(arg2, 0);
            Assert.assertNotNull(arg3);
        }

        public static void main(String[] args) throws CmdLineException {
            MethodCmdLineParser.invoke(ClassWithMain.class, args);
        }
    }


    private static String toStringWithType(Object o) {
        return getClassName(o) + ": " + o +"\n";
    }

    private static String getClassName(Object o) {
        return o == null ? "NULL" : o.getClass().getSimpleName();
    }

    @Test
    public void testInvoke() throws Exception {
        Object result = MethodCmdLineParser.invoke(
                MethodCmdLineParserTest.class,
                "-f",
                "Hello",
                "/tmp",
                "3");
        // assert no exception
    }

    @Test
    public void testInvokeClass() throws Exception {
        ClassWithMain.mainInvoked = false;

        ClassWithMain.main(new String[]{ "/tmp", "3", "rest1", "rest2" });
        // assert no exception

        Assert.assertTrue(ClassWithMain.mainInvoked);
    }
}
