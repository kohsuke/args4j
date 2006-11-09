package org.kohsuke.args4j;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.DoubleOptionHandler;
import org.kohsuke.args4j.spi.EnumOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.StringOptionHandler;


/**
 * Command line argument owner.
 *
 * <p>
 * For a typical usage, see <a href="https://args4j.dev.java.net/source/browse/args4j/args4j/examples/SampleMain.java?view=markup">this example</a>.
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
    private final Map<String,OptionHandler> options = new TreeMap<String,OptionHandler>();

    /**
     * Discovered {@link OptionHandler}s for arguments.
     */
    private final List<OptionHandler> arguments = new ArrayList<OptionHandler>();
    
    private boolean parsingOptions = true;

	/**
	 *  The length of a usage line. 
	 *  If the usage message is longer than this value, the parser
	 *  wraps the line. Defaults to 80.
	 */
	private int usageWidth = 80;

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
                }
                Argument a = m.getAnnotation(Argument.class);
                if(a!=null) {
                    addArgument(new MethodSetter(bean,m),a);
                }
            }

            for( Field f : c.getDeclaredFields() ) {
                Option o = f.getAnnotation(Option.class);
                if(o!=null) {
                    addOption(createFieldSetter(f),o);
                }
                Argument a = f.getAnnotation(Argument.class);
                if(a!=null) {
                    addArgument(createFieldSetter(f),a);
                }
            }
        }
        for (int i=0;i<arguments.size();++i) {
        	if (arguments.get(i)==null) {
                throw new IllegalAnnotationError("No argument annotation for index "+i);
        	}
        }
    }

    private Setter createFieldSetter(Field f) {
        if(List.class.isAssignableFrom(f.getType()))
            return new MultiValueFieldSetter(bean,f);
        //else if(Map.class.isAssignableFrom(f.getType()))
        //    return new MapFieldSetter(bean,f);
        else
            return new FieldSetter(bean,f);
    }

    private void addArgument(Setter setter, Argument a) {
        OptionHandler h = createOptionHandler(new OptionDef(a,setter.isMultiValued()),setter);
    	int index = a.index();
    	// make sure the argument will fit in the list
    	while (index >= arguments.size()) {
    		arguments.add(null);
    	}
    	if(arguments.get(index)!=null) {
            throw new IllegalAnnotationError("Argument with index "+index+" is used more than once");
        }
    	arguments.set(index,h);
    }

    private void addOption(Setter setter, Option o) {
        OptionHandler h = createOptionHandler(new OptionDef(o,setter.isMultiValued()),setter);
        if(options.put(h.option.name(),h)!=null) {
            throw new IllegalAnnotationError("Option name "+h.option.name()+" is used more than once");
        }
    }

    /**
     * Creates an {@link OptionHandler} that handles the given {@link Option} annotation
     * and the {@link Setter} instance.
     */
    protected OptionHandler createOptionHandler(OptionDef o, Setter setter) {

        Constructor<? extends OptionHandler> handlerType;
        Class<? extends OptionHandler> h = o.handler();
        if(h==OptionHandler.class) {
            // infer the type

            // enum is the special case
            Class t = setter.getType();
            if(Enum.class.isAssignableFrom(t))
                return new EnumOptionHandler(this,o,setter,t);

            handlerType = handlerClasses.get(t);
            if(handlerType==null)
                throw new IllegalAnnotationError("No OptionHandler is registered to handle "+t);
        } else {
            handlerType = getConstructor(h);
        }

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
     * Formats a command line example into a string.
     *
     * See {@link #printExample(ExampleMode, ResourceBundle)} for more details.
     *
     * @param mode
     *      must not be null.
     * @return
     *      always non-null.
     */
    public String printExample(ExampleMode mode) {
        return printExample(mode,null);
    }

    /**
     * Formats a command line example into a string.
     *
     * <p>
     * This method produces a string like " -d &lt;dir> -v -b",
     * which is useful for printing a command line example, perhaps
     * as a part of the usage screen.
     *
     *
     * @param mode
     *      One of the {@link ExampleMode} constants. Must not be null.
     *      This determines what option should be a part of the returned string.
     * @param rb
     *      If non-null, meta variables (&lt;dir> in the above example)
     *      is treated as a key to this resource bundle, and the associated
     *      value is printed. See {@link Option#metaVar()}. This is to support
     *      localization.
     *
     *      Passing <tt>null</tt> would print {@link Option#metaVar()} directly.
     * @return
     *      always non-null. If there's no option, this method returns
     *      just the empty string "". Otherwise, this method returns a
     *      string that contains a space at the beginning (but not at the end.)
     *      This allows you to do something like:
     *
     *      <pre>System.err.println("java -jar my.jar"+parser.printExample(REQUIRED)+" arg1 arg2");</pre> 
     */
    public String printExample(ExampleMode mode,ResourceBundle rb) {
        StringBuilder buf = new StringBuilder();

        for (Map.Entry<String, OptionHandler> e : options.entrySet()) {
            OptionDef option = e.getValue().option;
            if(option.usage().length()==0)  continue;   // ignore
            if(!mode.print(option))         continue;

            buf.append(' ');
            buf.append(e.getKey());

            String metaVar = e.getValue().getMetaVariable(rb);
            if(metaVar!=null) {
                buf.append(' ').append(metaVar);
            }
        }

        return buf.toString();
    }

    /**
     * Prints the list of options and their usages to the screen.
     *
     * <p>
     * This is a convenience method for calling {@code printUsage(new OutputStreamWriter(out),null)}
     * so that you can do {@code printUsage(System.err)}.
     */
    public void printUsage(OutputStream out) {
        printUsage(new OutputStreamWriter(out),null);
    }
    /**
     * Prints the list of options and their usages to the screen.
     *
     * @param rb
     *      if this is non-null, {@link Option#usage()} is treated
     *      as a key to obtain the actual message from this resource bundle.
     */
    public void printUsage(Writer out, ResourceBundle rb) {
        PrintWriter w = new PrintWriter(out);
        // determine the length of the option + metavar first
        int len = 0;
        for (OptionHandler h : arguments) {
            int curLen = getPrefixLen(h, rb);
            len = Math.max(len,curLen);
        }
        for (OptionHandler h: options.values()) {
            int curLen = getPrefixLen(h, rb);
            len = Math.max(len,curLen);
        }

        // then print
        for (OptionHandler h : arguments) {
        	printOption(w, h, len, rb);
        }
        for (OptionHandler h : options.values()) {
        	printOption(w, h, len, rb);
        }

        w.flush();
    }
    
    private void printOption(PrintWriter w, OptionHandler h, int len, ResourceBundle rb) {
        int descriptionWidth = usageWidth-len-4;    // 3 for " : " + 1 for left-most SP

        String usage = h.option.usage();
        if(usage.length()==0)   return;   // ignore

        int headLen = h.option.name().length();
        w.print(' ');
       	w.print(h.option.name());

        String metaVar = h.getMetaVariable(rb);
        if(metaVar!=null) {
        	if (h.option.name().length() != 0)
        		w.print(' ');
            w.print(metaVar);
            headLen += metaVar.length()+1;
        }
        for( ; headLen<len; headLen++ )
            w.print(' ');
        w.print(" : ");

        if(rb!=null)
            usage = rb.getString(usage);

        while(usage!=null && usage.length()>0) {
            int idx = usage.indexOf('\n');
            if(idx>=0 && idx<=descriptionWidth) {
                w.println(usage.substring(0,idx));
                usage = usage.substring(idx+1);
                if(usage.length()>0)
                    indent(w,len+4);
                continue;
            }
            if(usage.length()<=descriptionWidth) {
                w.println(usage);
                break;
            }

            w.println(usage.substring(0,descriptionWidth));
            usage = usage.substring(descriptionWidth);
            indent(w,len+4);
        }
    }

	private int getPrefixLen(OptionHandler h, ResourceBundle rb) {
		if(h.option.usage().length()==0)
			return 0;
         
		String name = h.option.name();
		int curLen = 0;	
		String metaVar = h.getMetaVariable(rb);
		if (metaVar != null)
			curLen += metaVar.length();
		curLen += name.length();
		if (metaVar != null)
			curLen++;
		return curLen;
	}

    private void indent(PrintWriter w, int i) {
        for( ; i>0; i-- )
            w.print(' ');
    }


    /**
     * Essentially a pointer over a {@link String} array.
     * Can move forward, can look ahead.
     */
    private class CmdLineImpl implements Parameters {
        private final String[] args;
        private int pos;

        CmdLineImpl( String[] args ) {
            this.args = args;
            pos = 0;
        }

        protected boolean hasMore() {
            return pos<args.length;
        }

        protected String getCurrentToken() {
            return args[pos];
        }

        private void proceed( int n ) {
            pos += n;
        }

        private String getOptionName() {
            return args[pos-1];
        }

        public String getParameter(int idx) throws CmdLineException {
			if( pos+idx>=args.length )
                throw new CmdLineException(Messages.MISSING_OPERAND.format(getOptionName()));
            return args[pos+idx];
        }
    }

    /**
     * Parses the command line arguments and set them to the option bean
     * given in the constructor.
     * 
     * @param args arguments to parse
     *
     * @throws CmdLineException
     *      if there's any error parsing arguments, or if
     *      {@link Option#required() required} option was not given.
     */
    public void parseArgument(final String... args) throws CmdLineException {
        CmdLineImpl cmdLine = new CmdLineImpl(args);

        Set<OptionHandler> present = new HashSet<OptionHandler>();
        int argIndex = 0;

        while( cmdLine.hasMore() ) {
            String arg = cmdLine.getCurrentToken();
            OptionHandler handler = null;
            if( isOption(arg) ) {
                // parse this as an option.
                handler = (arg.indexOf('=')==-1) 
                                      ? options.get(arg)         // normal option
                                      : findOptionHandler(arg);  // key=value pair
                
                if(handler==null) {
                    // TODO: insert dynamic handler processing
                    throw new CmdLineException(Messages.UNDEFINED_OPTION.format(arg));
                }

                // known option; skip its name
                cmdLine.proceed(1);
            } else {
            	if (argIndex >= arguments.size()) {
            		Messages msg = arguments.size() == 0 ? Messages.NO_ARGUMENT_ALLOWED : Messages.TOO_MANY_ARGUMENTS;
                    throw new CmdLineException(msg.format(arg));
            	}

            	// known argument
            	handler = arguments.get(argIndex); 
            	if (!handler.option.isMultiValued())
            		argIndex++;
            }
        	int diff = handler.parseArguments(cmdLine);
        	cmdLine.proceed(diff);
        	present.add(handler);
        }

        // make sure that all mandatory options are present
        for (OptionHandler handler : options.values())
            if(handler.option.required() && !present.contains(handler))
                throw new CmdLineException(Messages.REQUIRED_OPTION_MISSING.format(handler.option.name()));

        // make sure that all mandatory arguments are present
        for (OptionHandler handler : arguments)
            if(handler.option.required() && !present.contains(handler))
                throw new CmdLineException(Messages.REQUIRED_ARGUMENT_MISSING.format(handler.option.metaVar()));
    }
    
	private OptionHandler findOptionHandler(String name) {
		OptionHandler handler = options.get(name);
		if (handler==null) {
			// Have not found by its name, maybe its a property?
			// Search for parts of the name (=prefix) - most specific first 
			for (int i=name.length(); i>1; i--) {
				String prefix = name.substring(0, i);
				Map<String,OptionHandler> possibleHandlers = filter(options, prefix);
				handler = possibleHandlers.get(prefix);
				if (handler!=null) return handler;
			}
		}
		return handler;
	}
	
	
	private Map<String,OptionHandler> filter(Map<String,OptionHandler> map, String keyFilter) {
		Map<String,OptionHandler> rv = new TreeMap<String,OptionHandler>();
		for (String key : map.keySet()) {
			if (key.startsWith(keyFilter)) rv.put(key, map.get(key));
		}
		return rv;
	}

	
    /**
     * Returns true if the given token is an option
     * (as opposed to an argument.)
     */
    protected boolean isOption(String arg) {
        return parsingOptions && arg.startsWith("-");
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
     *      {@link OptionHandler#OptionHandler(CmdLineParser, OptionDef, Setter)}.
     */
    public static void registerHandler( Class valueType, Class<? extends OptionHandler> handlerClass ) {
        if(valueType==null || handlerClass==null)
            throw new IllegalArgumentException();
        if(!OptionHandler.class.isAssignableFrom(handlerClass))
            throw new IllegalArgumentException("Not an OptionHandler class");

        Constructor<? extends OptionHandler> c = getConstructor(handlerClass);
        handlerClasses.put(valueType,c);
    }

    private static Constructor<? extends OptionHandler> getConstructor(Class<? extends OptionHandler> handlerClass) {
        try {
            return handlerClass.getConstructor(CmdLineParser.class, OptionDef.class, Setter.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(handlerClass+" does not have the proper constructor");
        }
    }

    static {
        registerHandler(Boolean.class,BooleanOptionHandler.class);
        registerHandler(boolean.class,BooleanOptionHandler.class);
        registerHandler(File.class,FileOptionHandler.class);
        registerHandler(Integer.class,IntOptionHandler.class);
        registerHandler(int.class,IntOptionHandler.class);
        registerHandler(Double.class, DoubleOptionHandler.class);
        registerHandler(double.class,DoubleOptionHandler.class);
        registerHandler(String.class,StringOptionHandler.class);
        // enum is a special case
        //registerHandler(Map.class,MapOptionHandler.class);
    }

	public void setUsageWidth(int usageWidth) {
		this.usageWidth = usageWidth;
	}
	
	public void stopOptionParsing() {
		parsingOptions = false;
	}

    /**
     * Prints a single-line usage to the screen.
     *
     * <p>
     * This is a convenience method for calling {@code printUsage(new OutputStreamWriter(out),null)}
     * so that you can do {@code printUsage(System.err)}.
     */
	public void printSingleLineUsage(OutputStream out) {
		printSingleLineUsage(new OutputStreamWriter(out),null);
	}
	
    /**
     * Prints a single-line usage to the screen.
     *
     * @param rb
     *      if this is non-null, {@link Option#usage()} is treated
     *      as a key to obtain the actual message from this resource bundle.
     */
	public void printSingleLineUsage(Writer w, ResourceBundle rb) {
		PrintWriter pw = new PrintWriter(w);
		for (OptionHandler h : arguments) {
			printSingleLineOption(pw, h, rb);
		}
		for (OptionHandler h : options.values()) {
			printSingleLineOption(pw, h, rb);
		}
		pw.flush();
	}

	private void printSingleLineOption(PrintWriter pw, OptionHandler h, ResourceBundle rb) {
		String metaVar = h.getMetaVariable(rb);
		pw.print(' ');
		if (!h.option.required())
			pw.print('[');
		pw.print(h.option.name());
		if (metaVar != null) {
			if (h.option.name().length() > 0)
				pw.print(' ');
			pw.print(metaVar);
		}
		if (h.option.isMultiValued()) {
			pw.print(" ...");
		}
		if (!h.option.required())
			pw.print(']');
	} 
}
