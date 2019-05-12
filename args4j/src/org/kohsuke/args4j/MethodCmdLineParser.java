package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.ArgumentImpl;
import org.kohsuke.args4j.spi.ArraySetter;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * A variation of {@link CmdLineParser} that can parse {@link Argument} and {@link Option}
 * annotations on arguments of a {@link Method}, as opposed to fields of a dedicated bean
 *
 * Example usage:
 * <code>
 *     class Main {
 *       public static void main(String[] args) {
 *          MethodCmdLineParser.invoke(Main.class, args)
 *       }
 *
 *       public static void typesafeMain(
 *          @Argument(usage="...") File arg1,
 *          @Option(name="flag", usage="...") boolean flag,
 *          Integer alsoAnArgument,
 *          @Nullable String optionalArgument) {
 *          ...
 *       }
 *     }
 * </code>
 */
public class MethodCmdLineParser extends CmdLineParser {

    public MethodCmdLineParser(
            Method method,
            Object[] output,
            ParserProperties parserProperties) {
        super(null, parserProperties);

        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterTypes.length; i++) {
            ArraySetter arraySetter = new ArraySetter(output, i, parameterTypes[i]);
            Option optionAnnotation = findAnnotation(parametersAnnotations[i], Option.class);
            if (optionAnnotation != null) {
                addOption(arraySetter, optionAnnotation);
            } else {
                addArgument(
                    arraySetter,
                    getArgumentAnnotation(i, parametersAnnotations, parameterTypes));
            }
        }
    }

    public static Object invoke(Method method, String... args) throws CmdLineException {
        return invoke(method, ParserProperties.defaults(), args);
    }

    public static Object invoke(
            Method method,
            ParserProperties parserProperties,
            String... args) throws CmdLineException {
        Object[] values = new Object[method.getParameterTypes().length];

        new MethodCmdLineParser(method, values, parserProperties)
                .parseArgument(args);

        try {
            method.setAccessible(true);
            return method.invoke(null, values);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArgumentImpl getArgumentAnnotation(
            int i,
            Annotation[][] parametersAnnotations,
            Class<?>[] parameterTypes) {
        Annotation[] annotations = parametersAnnotations[i];
        boolean explicitlyNullable = findAnnotation(annotations, "Nullable") != null;
        boolean explicitlyNotNull = findAnnotation(annotations, "NotNull") != null
                || findAnnotation(annotations, "NonNull") != null;
        try {
            Class<?> type = parameterTypes[i];
            ArgumentImpl argument =
                    ArgumentImpl.copyOf(findAnnotation(annotations, Argument.class));
            argument = argument != null ? argument : new ArgumentImpl();
            argument.index = i;
            if (explicitlyNullable) {
                argument.required = false;
            } else if (explicitlyNotNull) {
                argument.required = true;
            } else {
                // leave as is
            }
            if (isLast(i, parameterTypes) && type.equals(String.class)) {
                argument.handler = RestOfArgumentsHandler.class;
            }
            return argument;
        } catch (ClassNotFoundException c) {
            throw new RuntimeException(c);
        }
    }

    private static Annotation findAnnotation(Annotation[] parameterAnnotations, String simpleName) {
        for (Annotation annotation : parameterAnnotations) {
            if (annotation.annotationType().getSimpleName().equals(simpleName)) {
                return annotation;
            }
        }
        return null;
    }

    private static <T extends Annotation> T findAnnotation(Annotation[] parameterAnnotations, Class<T> c) {
        for (Annotation annotation : parameterAnnotations) {
            if (c.isInstance(annotation)) {
                return (T) annotation;
            }
        }
        return null;
    }

    private static boolean isLast(int i, Object[] array) {
        return i + 1 == array.length;
    }

    public static Method getMethod(Class c, String methodName) {
        for (Method method : c.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static Object invoke(Class hasSinglePublicStaticMethod, String... args) throws CmdLineException {
        return invoke(getMainMethod(hasSinglePublicStaticMethod), args);
    }

    public static Method getMainMethod(Class hasSinglePublicStaticMethod) {
        Method stringMain = null;
        Method result = null;
        for (Method current : hasSinglePublicStaticMethod.getDeclaredMethods()) {
            int modifiers = current.getModifiers();
            if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
                continue;
            }
            if (current.getName().equals("main") &&
                    Arrays.equals(current.getParameterTypes(), new Class[]{String[].class})) {
                stringMain = current;
                continue;
            }
            if (result != null) {
                throw new IllegalArgumentException("Multiple suitable methods found:\n" + result + "\n" + current);
            }
            result = current;
        }
        if (result == null) {
            result = stringMain;
        }
        if (result == null) {
            throw new IllegalArgumentException("No suitable method found in " + hasSinglePublicStaticMethod);
        }
        return result;
    }
}
