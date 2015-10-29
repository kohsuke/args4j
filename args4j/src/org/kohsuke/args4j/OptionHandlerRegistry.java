package org.kohsuke.args4j;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.ByteOptionHandler;
import org.kohsuke.args4j.spi.CharOptionHandler;
import org.kohsuke.args4j.spi.DoubleOptionHandler;
import org.kohsuke.args4j.spi.EnumOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.FloatOptionHandler;
import org.kohsuke.args4j.spi.InetAddressOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.LongOptionHandler;
import org.kohsuke.args4j.spi.MapOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.PathOptionHandler;
import org.kohsuke.args4j.spi.PatternOptionHandler;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.ShortOptionHandler;
import org.kohsuke.args4j.spi.StringOptionHandler;
import org.kohsuke.args4j.spi.URIOptionHandler;
import org.kohsuke.args4j.spi.URLOptionHandler;

import static org.kohsuke.args4j.Utilities.checkNonNull;

/**
 * Manages the registration of option handlers.
 * This is good for registering custom handlers
 * for specific parameter classes not yet implemented.
 * The registry is a singleton that can be
 * retrieved with the {@link #getRegistry()} call.
 * @author Stephan Fuhrmann
 */
public class OptionHandlerRegistry {

    /**
     * The shared reference.
     * @see #getRegistry() 
     */
    private static OptionHandlerRegistry instance;
    
    /**
     * Gets the option handler registry singleton instance.
     * @return a shared instance of the registry.
     */
    public synchronized static OptionHandlerRegistry getRegistry() {
        if (instance == null) {
            instance = new OptionHandlerRegistry();
        }
        return instance;
    }
    
    /**
     * Constructs an option handler manager with the
     * default handlers initialized.
     */
    private OptionHandlerRegistry() {
        initHandlers();
    }

    /** Registers the default handlers. */
    private void initHandlers() {
        registerHandler(Boolean.class,BooleanOptionHandler.class);
        registerHandler(boolean.class,BooleanOptionHandler.class);
        registerHandler(File.class,FileOptionHandler.class);
        registerHandler(URL.class, URLOptionHandler.class);
        registerHandler(URI.class, URIOptionHandler.class);
        registerHandler(Integer.class,IntOptionHandler.class);
        registerHandler(int.class,IntOptionHandler.class);
        registerHandler(Double.class, DoubleOptionHandler.class);
        registerHandler(double.class,DoubleOptionHandler.class);
        registerHandler(String.class,StringOptionHandler.class);
        registerHandler(Byte.class, ByteOptionHandler.class);
        registerHandler(byte.class, ByteOptionHandler.class);
        registerHandler(Character.class, CharOptionHandler.class);
        registerHandler(char.class, CharOptionHandler.class);
        registerHandler(Float.class, FloatOptionHandler.class);
        registerHandler(float.class, FloatOptionHandler.class);
        registerHandler(Long.class, LongOptionHandler.class);
        registerHandler(long.class, LongOptionHandler.class);
        registerHandler(Short.class, ShortOptionHandler.class);
        registerHandler(short.class, ShortOptionHandler.class);
        registerHandler(InetAddress.class, InetAddressOptionHandler.class);
        registerHandler(Pattern.class, PatternOptionHandler.class);
        // enum is a special case
        registerHandler(Map.class,MapOptionHandler.class);

        try {
            Class p = Class.forName("java.nio.file.Path");
            registerHandler(p, PathOptionHandler.class);
        } catch (ClassNotFoundException e) {
            // running in Java6 or earlier
        }
    }
    
    /** Finds the constructor for an option handler. 
     */
    private static Constructor<? extends OptionHandler> getConstructor(Class<? extends OptionHandler> handlerClass) {
        try {
            return handlerClass.getConstructor(CmdLineParser.class, OptionDef.class, Setter.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(Messages.NO_CONSTRUCTOR_ON_HANDLER.format(handlerClass));
        }
    }
    
    /**
     * Registers a user-defined {@link OptionHandler} class with args4j.
     *
     * <p>
     * This method allows users to extend the behavior of args4j by writing
     * their own {@link OptionHandler} implementation.
     *
     * @param valueType
     *      The specified handler is used when the field/method annotated by {@link Option}
     *      is of this type.
     * @param handlerClass
     *      This class must have the constructor that has the same signature as
     *      {@link OptionHandler#OptionHandler(CmdLineParser, OptionDef, Setter)}
     * @throws NullPointerException if {@code valueType} or {@code handlerClass} is {@code null}.
     * @throws IllegalArgumentException if {@code handlerClass} is not a subtype of {@code OptionHandler}.
     */
    public void registerHandler( Class valueType, Class<? extends OptionHandler> handlerClass ) {
        checkNonNull(valueType, "valueType");
        checkNonNull(handlerClass, "handlerClass");

        if(!OptionHandler.class.isAssignableFrom(handlerClass))
            throw new IllegalArgumentException(Messages.NO_OPTIONHANDLER.format());

        handlers.put(valueType, new DefaultConstructorHandlerFactory(handlerClass));
    }

    /**
     * Registers a user-defined {@link OptionHandler} class with args4j.
     *
     * <p>
     * This method allows users to extend the behavior of args4j by writing
     * their own {@link OptionHandler} implementation.
     *
     * @param valueType
     *      The specified handler is used when the field/method annotated by {@link Option}
     *      is of this type.
     * @param factory
     *      Factory to instantiate handler upon request.
     * @throws NullPointerException if {@code valueType} or {@code factory} is {@code null}.
     */
    public void registerHandler(Class valueType, OptionHandlerFactory factory) {
        checkNonNull(valueType, "valueType");
        checkNonNull(factory, "factory");

        handlers.put(valueType, factory);
    }

    /**
     * Creates an {@link OptionHandler} that handles the given {@link Option} annotation
     * and the {@link Setter} instance.
     */
    @SuppressWarnings("unchecked")
    protected OptionHandler createOptionHandler(CmdLineParser parser, OptionDef o, Setter setter) {
        checkNonNull(o, "CmdLineParser");
        checkNonNull(o, "OptionDef");
        checkNonNull(setter, "Setter");

        Class<? extends OptionHandler> h = o.handler();
        if(h==OptionHandler.class) {
            // infer the type
            Class<?> t = setter.getType();

            // enum is the special case
            if(Enum.class.isAssignableFrom(t))
                return new EnumOptionHandler(parser,o,setter,t);

            OptionHandlerFactory factory = handlers.get(t);
            if (factory==null)
                throw new IllegalAnnotationError(Messages.UNKNOWN_HANDLER.format(t));

            return factory.getHandler(parser, o, setter);
        } else {
            // explicit handler specified
            return new DefaultConstructorHandlerFactory(h).getHandler(parser, o, setter);
        }
    }

    /**
     * All {@link OptionHandler}s known to the {@link CmdLineParser}.
     *
     * Constructors of {@link OptionHandler}-derived class keyed by their supported types.
     */
    private final Map<Class, OptionHandlerFactory> handlers =
            Collections.synchronizedMap(new HashMap<Class, OptionHandlerFactory>());

    /**
     * Provide custom logic for creating {@link OptionHandler} implementation.
     *
     * @author ogondza
     */
    public interface OptionHandlerFactory {
        /**
         * Provide a handler instance to use.
         */
        OptionHandler<?> getHandler(CmdLineParser parser, OptionDef o, Setter setter);
    }

    private class DefaultConstructorHandlerFactory implements OptionHandlerFactory {

        private final Constructor<? extends OptionHandler> cons;

        public DefaultConstructorHandlerFactory(Class type) {
            this.cons = getConstructor(type);
        }

        public OptionHandler<?> getHandler(CmdLineParser parser, OptionDef o, Setter setter) {
            try {
                return cons.newInstance(parser, o, setter);
            } catch (InstantiationException e) {
                throw new IllegalAnnotationError(e);
            } catch (IllegalAccessException e) {
                throw new IllegalAnnotationError(e);
            } catch (InvocationTargetException e) {
                throw new IllegalAnnotationError(e);
            }
        }
    }
}
