package org.kohsuke;

import org.kohsuke.opts.EnumOptionHandler;
import org.kohsuke.opts.OptionHandler;
import org.kohsuke.opts.Parameters;
import org.kohsuke.opts.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 * Command line argument owner.
 *
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class CmdLineParser {
    /**
     * Option bean instance.
     */
    private final Object bean;

    /**
     * Discovered {@link OptionHandler}s keyed by their option names.
     */
    private final Map<String,OptionHandler> options = new HashMap<String,OptionHandler>();

    /**
     * {@link Setter} that accepts the arguments.
     */
    private Setter argumentSetter;

    /**
     * Creates a new command line owner that
     * parses arguments/options and set them into
     * the given object.
     *
     * @param bean
     *      instance of a class annotated by {@link Option} and {@link Argument}.
     *      this object will receive values.
     *
     * @throws IllegalAnnotationError
     *      if the option bean class is using args4j annotations incorrectly.
     */
    public CmdLineParser(Object bean) {
        this.bean = bean;

        // recursively process all the methods/fields.
        for( Class c=bean.getClass(); c!=null; c=c.getSuperclass()) {
            for( Method m : c.getDeclaredMethods() ) {
                Option o = m.getAnnotation(Option.class);
                if(o!=null) {
                    addOption(new MethodSetter(bean,m),o);
                    continue;
                }
                Argument a = m.getAnnotation(Argument.class);
                if(a!=null) {
                    addArgument(new MethodSetter(bean,m));
                    continue;
                }
            }

            for( Field f : c.getDeclaredFields() ) {
                Option o = f.getAnnotation(Option.class);
                if(o!=null) {
                    addOption(createFieldSetter(f),o);
                    continue;
                }
                Argument a = f.getAnnotation(Argument.class);
                if(a!=null) {
                    addArgument(createFieldSetter(f));
                    continue;
                }
            }
        }
    }

    private Setter createFieldSetter(Field f) {
        if(List.class.isAssignableFrom(f.getType()))
            return new MultiValueFieldSetter(bean,f);
        else
            return new FieldSetter(bean,f);
    }

    private void addArgument(Setter setter) {
        if(argumentSetter!=null)
            throw new IllegalAnnotationError("@Argument is used more than once");
        argumentSetter = setter;
    }

    private void addOption(Setter setter, Option o) {
        OptionHandler h = createOptionHandler(o,setter);
        if(options.put(h.option.name(),h)!=null) {
            throw new IllegalAnnotationError("Option name "+h.option.name()+" is used more than once");
        }
    }

    /**
     * Creates an {@link OptionHandler} that handles the given {@link Option} annotation
     * and the {@link Setter} instance.
     */
    protected OptionHandler createOptionHandler(Option o, Setter setter) {
        // enum is the special case
        Class t = setter.getType();

        if(Enum.class.isAssignableFrom(t))
            return new EnumOptionHandler(this,o,setter,t);

        Constructor<? extends OptionHandler> handlerType = handlerClasses.get(t);
        if(handlerType==null)
            throw new IllegalAnnotationError("No OptionHandler is registered to handle "+t);
        try {
            return handlerType.newInstance(this,o,setter);
        } catch (InstantiationException e) {
            throw new IllegalAnnotationError(e);
        } catch (IllegalAccessException e) {
            throw new IllegalAnnotationError(e);
        } catch (InvocationTargetException e) {
            throw new IllegalAnnotationError(e);
        }
    }




    /**
     * Essentially a pointer over a {@link String} array.
     * Can move forward, can look ahead.
     */
    private class CmdLineImpl extends Parameters {
        private final String[] args;
        private int pos;

        CmdLineImpl( String[] args ) {
            this.args = args;
            pos = 0;
        }

        private boolean hasMore() {
            return pos<args.length;
        }

        private String getCurrentToken() {
            return args[pos];
        }

        private void proceed( int n ) {
            pos += n;
        }


        public String getOptionName() {
            return getCurrentToken();
        }

        public String getParameter(int idx) throws CmdLineException {
            if( pos+idx+1>=args.length )
                throw new CmdLineException(Messages.MISSING_OPERAND.format(getOptionName()));
            return args[pos+idx+1];
        }
    }

    /**
     * Parses the command line arguments and set them to the option bean
     * given in the constructor.
     *
     * @throws CmdLineException
     *      if there's any error parsing arguments.
     */
    public void parseArgument(final String... args) throws CmdLineException {
        CmdLineImpl cmdLine = new CmdLineImpl(args);

        while( cmdLine.hasMore() ) {
            String arg = cmdLine.getOptionName();
            if( isOption(arg) ) {
                // parse this as an option.
                OptionHandler handler = options.get(arg);
                if(handler!=null) {
                    // known option
                    int diff = handler.parseArguments(cmdLine);
                    cmdLine.proceed(diff);
                    continue;
                }

                // TODO: insert dynamic handler processing

                throw new CmdLineException(Messages.UNDEFINED_OPTION.format(arg));
            } else {
                // parse this as arguments
                if(argumentSetter==null)
                    throw new CmdLineException(Messages.NO_ARGUMENT_ALLOWED.format(arg));
                argumentSetter.addValue(arg);
                cmdLine.proceed(1);
            }
        }
    }

    /**
     * Returns true if the given token is an option
     * (as opposed to an argument.)
     */
    protected boolean isOption(String arg) {
        return arg.startsWith("-");
    }


    /**
     * All {@link OptionHandler}s known to the {@link CmdLineParser}.
     *
     * Constructors of {@link OptionHandler}-derived class keyed by their supported types.
     */
    private static final Map<Class,Constructor<? extends OptionHandler>> handlerClasses =
            Collections.synchronizedMap(new HashMap<Class,Constructor<? extends OptionHandler>>());

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
     *      {@link OptionHandler#OptionHandler(CmdLineParser, Option, Setter)}.
     */
    public static void registerHandler( Class valueType, Class<? extends OptionHandler> handlerClass ) {
        if(valueType==null || handlerClass==null)
            throw new IllegalArgumentException();
        if(!OptionHandler.class.isAssignableFrom(handlerClass))
            throw new IllegalArgumentException("Not an OptionHandler class");

        try {
            Constructor<? extends OptionHandler> c = handlerClass.getConstructor(CmdLineParser.class,Option.class,Setter.class);
            handlerClasses.put(valueType,c);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(handlerClass+" does not have the proper constructor");
        }
    }
}
