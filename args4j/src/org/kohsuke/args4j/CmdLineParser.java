package org.kohsuke.args4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
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
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.PathOptionHandler;
import org.kohsuke.args4j.spi.PatternOptionHandler;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.ShortOptionHandler;
import org.kohsuke.args4j.spi.StringOptionHandler;
import org.kohsuke.args4j.spi.URIOptionHandler;
import org.kohsuke.args4j.spi.URLOptionHandler;


/**
 * Command line argument owner.
 *
 * <p>
 * For typical usage, see <a href="https://args4j.dev.java.net/source/browse/args4j/args4j/examples/SampleMain.java?view=markup">this example</a>.
 *
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class CmdLineParser {
    /**
     * Discovered {@link OptionHandler}s for options.
     */
    private final List<OptionHandler> options = new ArrayList<OptionHandler>();

    /**
     * Discovered {@link OptionHandler}s for arguments.
     */
    private final List<OptionHandler> arguments = new ArrayList<OptionHandler>();

    private boolean parsingOptions = true;
    private OptionHandler currentOptionHandler = null;

	/**
     * settings for the parser
	 */
	private ParserProperties parserProperties;

    /**
     * Creates a new command line owner that
     * parses arguments/options and set them into
     * the given object.
     *
     * @param bean
     *      instance of a class annotated by {@link Option} and {@link Argument}.
     *      this object will receive values. If this is {@code null}, the processing will
     *      be skipped, which is useful if you'd like to feed metadata from other sources.
     *
     * @throws IllegalAnnotationError
     *      if the option bean class is using args4j annotations incorrectly.
     */
    public CmdLineParser(Object bean) {
        // for display purposes, we like the arguments in argument order, but the options in alphabetical order
        this(bean, ParserProperties.defaults());
    }

    /**
     * Creates a new command line owner that
     * parses arguments/options and set them into
     * the given object.
     *
     * @param bean
     *      instance of a class annotated by {@link Option} and {@link Argument}.
     *      this object will receive values. If this is {@code null}, the processing will
     *      be skipped, which is useful if you'd like to feed metadata from other sources.
     *
     * @param parserProperties various settings for this class
     *
     * @throws IllegalAnnotationError
     *      if the option bean class is using args4j annotations incorrectly.
     */
    public CmdLineParser(Object bean, ParserProperties parserProperties) {
        this.parserProperties = parserProperties;
        // A 'return' in the constructor just skips the rest of the implementation
        // and returns the new object directly.
        if (bean==null) return;

        // Parse the metadata and create the setters
        new ClassParser().parse(bean,this);

        if (parserProperties.optionSorter()!=null) {
            Collections.sort(options, parserProperties.optionSorter());
        }
    }

    /** This method is similar to {@code Objects.requireNonNull()}.
     * But this one is available for JDK 1.6 which is the
     * current target of args4j.
     * I didn't want to break compatibility with JDK 1.6.
     * @param obj the object to check for {@code null} value.
     * @param name the object name. If {@code obj} is {@code null}, then
     * an exception is constructed from this name.
     */
    private static void checkNonNull(Object obj, String name) {
        if (obj == null) {
            throw new NullPointerException(name+" is null");
        }
    }

    /**
     * Programmatically defines an argument (instead of reading it from annotations as normal).
     *
     * @param setter the setter for the type
     * @param a the Argument
     * @throws NullPointerException if {@code setter} or {@code a} is {@code null}.
     */
    public void addArgument(Setter setter, Argument a) {
        checkNonNull(setter, "Setter");
        checkNonNull(a, "Argument");
        
        OptionHandler h = createOptionHandler(new OptionDef(a,setter.isMultiValued()),setter);
    	int index = a.index();
    	// make sure the argument will fit in the list
    	while (index >= arguments.size()) {
    		arguments.add(null);
    	}
    	if(arguments.get(index)!=null) {
            throw new IllegalAnnotationError(Messages.MULTIPLE_USE_OF_ARGUMENT.format(index));
        }
    	arguments.set(index, h);
    }

    /**
     * Programmatically defines an option (instead of reading it from annotations as normal).
     *
     * @param setter the setter for the type
     * @param o the {@code Option}
     * @throws NullPointerException if {@code setter} or {@code o} is {@code null}.
     * @throws IllegalAnnotationError if the option name or one of the aliases is already taken.
     */
    public void addOption(Setter setter, Option o) {
        checkNonNull(setter, "Setter");
        checkNonNull(o, "Option");
    
        checkOptionNotInMap(o.name());
        for (String alias : o.aliases()) {
        	checkOptionNotInMap(alias);
        }
        options.add(createOptionHandler(new NamedOptionDef(o), setter));
    }

    /**
     * Lists up all the defined arguments in the order.
     */
    public List<OptionHandler> getArguments() {
        return arguments;
    }

    /**
     * Lists up all the defined options.
     */
    public List<OptionHandler> getOptions() {
        return options;
    }

	private void checkOptionNotInMap(String name) throws IllegalAnnotationError {
        checkNonNull(name, "name");
        
		if(findOptionByName(name)!=null) {
            throw new IllegalAnnotationError(Messages.MULTIPLE_USE_OF_OPTION.format(name));
        }
	}

    /**
     * Creates an {@link OptionHandler} that handles the given {@link Option} annotation
     * and the {@link Setter} instance.
     */
   @SuppressWarnings("unchecked")
    protected OptionHandler createOptionHandler(OptionDef o, Setter setter) {
        checkNonNull(o, "OptionDef is null");
        checkNonNull(setter, "Setter is null");

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
                throw new IllegalAnnotationError(Messages.UNKNOWN_HANDLER.format(t));
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

    /** Create a command line help instance.
     */
    public CmdLineHelp createCmdLineHelp() {
        return new CmdLineHelp(this, parserProperties);
    }
    
    /**
     * Formats a command line example into a string.
     *
     * See {@link #printExample(OptionHandlerFilter, ResourceBundle)} for more details.
     *
     * @param filter
     *      must not be {@code null}.
     * @return
     *      always non-{@code null}.
     * @deprecated use {@link #createCmdLineHelp()} and its methods.
     */
    public String printExample(OptionHandlerFilter filter) {
        return createCmdLineHelp().printExample(filter);
    }

    /**
     * @deprecated
     *      Use {@link #printExample(OptionHandlerFilter)}
     */
    public String printExample(ExampleMode mode) {
        return printExample(mode, null);
    }

    /**
     * Formats a command line example into a string.
     *
     * <p>
     * This method produces a string like <samp> -d &lt;dir> -v -b</samp>.
     * This is useful for printing a command line example (perhaps
     * as a part of the usage screen).
     *
     *
     * @param mode
     *      Determines which options will be a part of the returned string.
     *      Must not be {@code null}.
     * @param rb
     *      If non-{@code null}, meta variables (<samp>&lt;dir></samp> in the above example)
     *      is treated as a key to this resource bundle, and the associated
     *      value is printed. See {@link Option#metaVar()}. This is to support
     *      localization.
     *
     *      Passing {@code null} would print {@link Option#metaVar()} directly.
     * @return
     *      always non-{@code null}. If there's no option, this method returns
     *      just the empty string {@code ""}. Otherwise, this method returns a
     *      string that contains a space at the beginning (but not at the end).
     *      This allows you to do something like:
     *      <code><pre>System.err.println("java -jar my.jar"+parser.printExample(REQUIRED)+" arg1 arg2");</pre></code>
     * @throws NullPointerException if {@code mode} is {@code null}.
     * @deprecated use {@link #createCmdLineHelp()} and its methods.
     */
    public String printExample(OptionHandlerFilter mode, ResourceBundle rb) {
        return createCmdLineHelp().printExample(mode, rb);
    }

    /**
     * @deprecated
     *      Use {@link #printExample(OptionHandlerFilter,ResourceBundle)}
     */
    public String printExample(ExampleMode mode, ResourceBundle rb) {
        return createCmdLineHelp().printExample((OptionHandlerFilter) mode, rb);
    }

    /**
     * Prints the list of options and their usages to the screen.
     *
     * <p>
     * This is a convenience method for calling {@code printUsage(new OutputStreamWriter(out),null)}
     * so that you can do {@code printUsage(System.err)}.
     * @deprecated use {@link #createCmdLineHelp()} and its methods.
     */
    public void printUsage(OutputStream out) {
        createCmdLineHelp().printUsage(new OutputStreamWriter(out),null);
    }

    /**
     * Prints the list of all the non-hidden options and their usages to the screen.
     *
     * <p>
     * Short for {@code printUsage(out,rb,OptionHandlerFilter.PUBLIC)}
     * @deprecated use {@link #createCmdLineHelp()} and its methods.
     */
    public void printUsage(Writer out, ResourceBundle rb) {
        createCmdLineHelp().printUsage(out, rb, OptionHandlerFilter.PUBLIC);
    }

    /**
     * Prints the list of all the non-hidden options and their usages to the screen.
     *
     * @param rb
     *      If non-{@code null}, {@link Option#usage()} is treated
     *      as a key to obtain the actual message from this resource bundle.
     * @param filter
     *      Controls which options to be printed.
     * @deprecated use {@link #createCmdLineHelp()} and its methods.
     */
    public void printUsage(Writer out, ResourceBundle rb, OptionHandlerFilter filter) {
        createCmdLineHelp().printUsage(out, rb, filter);
    }

    /**
     * Prints usage information for a given option.
     *
     * <p>
     * Subtypes may override this method and determine which options get printed (or other things),
     * based on {@link OptionHandler} (perhaps by using {@code handler.setter.asAnnotatedElement()}).
     *
     * @param out      Writer to write into
     * @param handler  handler where to receive the information
     * @param len      Maximum length of metadata column
     * @param rb       {@code ResourceBundle} for I18N
     * @see Setter#asAnnotatedElement()
     * @deprecated use {@link #createCmdLineHelp()} and its methods.
     */
    protected void printOption(PrintWriter out, OptionHandler handler, int len, ResourceBundle rb, OptionHandlerFilter filter) {
        createCmdLineHelp().printOption(out, handler, len, rb, filter);
    }

    /**
     * Essentially a pointer over a {@link String} array.
     * Can move forward; can look ahead.
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

        public String getParameter(int idx) throws CmdLineException {
			if( pos+idx>=args.length || pos+idx<0 )
                throw new CmdLineException(CmdLineParser.this, Messages.MISSING_OPERAND, getOptionName());
            return args[pos+idx];
        }

        public int size() {
            return args.length-pos;
        }

        /**
         * Used when the current token is of the form "-option=value",
         * to replace the current token by "value", as if this was given as two tokens "-option value"
         */
        void splitToken() {
            if (pos < args.length && pos >= 0) {
                int idx = args[pos].indexOf("=");
                if (idx > 0) {
                    args[pos] = args[pos].substring(idx + 1);
                }
            }
        }
    }

    private String getOptionName() {
        return currentOptionHandler.option.toString();
    }

    /**
     * Same as {@link #parseArgument(String[])}
     */
    public void parseArgument(Collection<String> args) throws CmdLineException {
        parseArgument(args.toArray(new String[args.size()]));
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
     * @throws NullPointerException if {@code args} is {@code null}.
     */
    public void parseArgument(final String... args) throws CmdLineException {
        
        checkNonNull(args, "args");
        
        String expandedArgs[] = expandAtFiles(args);
        CmdLineImpl cmdLine = new CmdLineImpl(expandedArgs);

        Set<OptionHandler> present = new HashSet<OptionHandler>();
        int argIndex = 0;

        while( cmdLine.hasMore() ) {
            String arg = cmdLine.getCurrentToken();
            if( isOption(arg) ) {
            	boolean isKeyValuePair = arg.indexOf('=')!=-1;
                // parse this as an option.
                currentOptionHandler = isKeyValuePair ? findOptionHandler(arg) : findOptionByName(arg);

                if(currentOptionHandler==null) {
                    // TODO: insert dynamic handler processing
                    throw new CmdLineException(this, Messages.UNDEFINED_OPTION, arg);
                }

                // known option; skip its name
                if (isKeyValuePair) {
                    cmdLine.splitToken();
                } else {
                    cmdLine.proceed(1);
                }
            } else {
            	if (argIndex >= arguments.size()) {
            		Messages msg = arguments.size() == 0 ? Messages.NO_ARGUMENT_ALLOWED : Messages.TOO_MANY_ARGUMENTS;
                    throw new CmdLineException(this, msg, arg);
            	}

            	// known argument
            	currentOptionHandler = arguments.get(argIndex);
                if (currentOptionHandler==null) // this is a programmer error. arg index should be continuous
                    throw new IllegalStateException("@Argument with index="+argIndex+" is undefined");

            	if (!currentOptionHandler.option.isMultiValued())
            		argIndex++;
            }
        	int diff = currentOptionHandler.parseArguments(cmdLine);
        	cmdLine.proceed(diff);
        	present.add(currentOptionHandler);
        }

        // check whether a help option is set
        boolean helpSet = false;
        for (OptionHandler handler : options) {
            if(handler.option.help() && present.contains(handler)) {
                helpSet = true;
            }
        }

        if (!helpSet) {
            checkRequiredOptionsAndArguments(present);
        }
    }
    
    /**
     * Expands every entry prefixed with the AT sign by
     * reading the file. The AT sign is used to reference
     * another file that contains command line options separated
     * by line breaks. 
     * @param args the command line arguments to be preprocessed.
     * @return args with the @ sequences replaced by the text files referenced
     * by the @ sequences, split around the line breaks.
     * @throws CmdLineException 
     */
    private String[] expandAtFiles(String args[]) throws CmdLineException {
        List<String> result = new ArrayList<String>();
        for (String arg : args) {
            if (arg.startsWith("@")) {
                File file = new File(arg.substring(1));
                if (!file.exists())
                    throw new CmdLineException(this,Messages.NO_SUCH_FILE,file.getPath());
                try {
                    result.addAll(readAllLines(file));
                } catch (IOException ex) {
                    throw new CmdLineException(this, "Failed to parse "+file,ex);
                }
            } else {
                result.add(arg);
            }
        }
        return result.toArray(new String[result.size()]);
    }
    
    /**
     * Reads all lines of a file with the platform encoding.
     */
    private static List<String> readAllLines(File f) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(f));
        try {
            List<String> result = new ArrayList<String>();
            String line;
            while ((line = r.readLine()) != null) {
                result.add(line);
            }
            return result;
        }  finally {
            r.close();
        }
    }

    private void checkRequiredOptionsAndArguments(Set<OptionHandler> present) throws CmdLineException {
        // make sure that all mandatory options are present
        for (OptionHandler handler : options) {
            if(handler.option.required() && !present.contains(handler)) {
                throw new CmdLineException(this, Messages.REQUIRED_OPTION_MISSING, handler.option.toString());
            }
        }

        // make sure that all mandatory arguments are present
        for (OptionHandler handler : arguments) {
            if(handler.option.required() && !present.contains(handler)) {
                throw new CmdLineException(this, Messages.REQUIRED_ARGUMENT_MISSING, handler.option.toString());
            }
        }

        //make sure that all requires arguments are present
        for (OptionHandler handler : present) {
            if (handler.option instanceof NamedOptionDef && !isHandlerHasHisOptions((NamedOptionDef)handler.option, present)) {
                throw new CmdLineException(this, Messages.REQUIRES_OPTION_MISSING,
                        handler.option.toString(), Arrays.toString(((NamedOptionDef)handler.option).depends()));
            }
        }
        
        //make sure that all forbids arguments are not present
        for (OptionHandler handler : present) {
            if (handler.option instanceof NamedOptionDef && !isHandlerAllowOtherOptions((NamedOptionDef) handler.option, present)) {
                throw new CmdLineException(this, Messages.FORBIDDEN_OPTION_PRESENT
                        .format(handler.option.toString(), Arrays.toString(((NamedOptionDef)handler.option).forbids())));
            }
        }
    }

    /**
     * @return {@code true} if all options required by {@code option} are present, {@code false} otherwise
     */
    private boolean isHandlerHasHisOptions(NamedOptionDef option, Set<OptionHandler> present) {
        for (String depend : option.depends()) {
            if (!present.contains(findOptionHandler(depend)))
                return false;
        }
        return true;
    }

    /**
     * @return {@code true} if all options forbid by {@code option} are not present, {@code false} otherwise
     */
    private boolean isHandlerAllowOtherOptions(NamedOptionDef option, Set<OptionHandler> present) {
        for (String forbid : option.forbids()) {
            if (present.contains(findOptionHandler(forbid)))
                return false;
        }
        return true;
    }
    
    private OptionHandler findOptionHandler(String name) {
		OptionHandler handler = findOptionByName(name);
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

	/**
	 * Finds a registered {@code OptionHandler} by its name or its alias.
	 * @param name name
	 * @return the {@code OptionHandler} or {@code null}
	 */
	private OptionHandler findOptionByName(String name) {
		for (OptionHandler h : options) {
			NamedOptionDef option = (NamedOptionDef)h.option;
			if (name.equals(option.name())) {
				return h;
			}
			for (String alias : option.aliases()) {
				if (name.equals(alias)) {
					return h;
				}
			}
		}
		return null;
	}


    private Map<String, OptionHandler> filter(List<OptionHandler> opt, String keyFilter) {
        Map<String, OptionHandler> rv = new TreeMap<String, OptionHandler>();
        for (OptionHandler h : opt) {
            NamedOptionDef option = (NamedOptionDef) h.option;
            String prefix = "";
            for (String alias : option.aliases()) {
                if (keyFilter.startsWith(alias)) {
                    prefix = keyFilter;
                    break;
                }
            }
            if (option.name().startsWith(keyFilter)) {
                prefix = keyFilter;
            }
            rv.put(prefix, h);
        }
        return rv;
    }


    /**
     * Returns {@code true} if the given token is an option
     * (as opposed to an argument).
     * @throws NullPointerException if {@code arg} is {@code null}.
     */
    protected boolean isOption(String arg) {
        checkNonNull(arg, "arg");
        
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
     *      {@link OptionHandler#OptionHandler(CmdLineParser, OptionDef, Setter)}
     * @throws NullPointerException if {@code valueType} or {@code handlerClass} is {@code null}.
     * @throws IllegalArgumentException if {@code handlerClass} is not a subtype of {@code OptionHandler}.
     */
    public static void registerHandler( Class valueType, Class<? extends OptionHandler> handlerClass ) {
        checkNonNull(valueType, "valueType");
        checkNonNull(handlerClass, "handlerClass");

        if(!OptionHandler.class.isAssignableFrom(handlerClass))
            throw new IllegalArgumentException(Messages.NO_OPTIONHANDLER.format());

        Constructor<? extends OptionHandler> c = getConstructor(handlerClass);
        handlerClasses.put(valueType,c);
    }

    private static Constructor<? extends OptionHandler> getConstructor(Class<? extends OptionHandler> handlerClass) {
        try {
            return handlerClass.getConstructor(CmdLineParser.class, OptionDef.class, Setter.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(Messages.NO_CONSTRUCTOR_ON_HANDLER.format(handlerClass));
        }
    }

    static {
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

    /**
     * Sets the width of the usage output.
     * @param usageWidth the width of the usage output in columns.
     * @throws IllegalArgumentException if {@code usageWidth} is negative
     * @deprecated
     *      Use {@link ParserProperties#withUsageWidth(int)} instead.
     */
	public void setUsageWidth(int usageWidth) {
        parserProperties.withUsageWidth(usageWidth);
	}

    /**
     * Signals the parser that parsing the options has finished.
     * 
     * <p>
     * Everything seen after this call is treated as an argument
     * as opposed to an option.
     */
	public void stopOptionParsing() {
		parsingOptions = false;
	}

    /**
     * Prints a single-line usage to the screen.
     *
     * <p>
     * This is a convenience method for calling {@code printUsage(new OutputStreamWriter(out),null)}
     * so that you can do {@code printUsage(System.err)}.
     * @throws NullPointerException if {@code out} is {@code null}.
     */
	public void printSingleLineUsage(OutputStream out) {
        checkNonNull(out, "OutputStream");
        
		printSingleLineUsage(new OutputStreamWriter(out), null);
	}

    /**
     * Prints a single-line usage to the screen.
     *
     * @param rb
     *      if this is non-{@code null}, {@link Option#usage()} is treated
     *      as a key to obtain the actual message from this resource bundle.
     * @throws NullPointerException if {@code w} is {@code null}.
     */
    // TODO test this!
	public void printSingleLineUsage(Writer w, ResourceBundle rb) {
        checkNonNull(w, "Writer");
        
		PrintWriter pw = new PrintWriter(w);
		for (OptionHandler h : arguments) {
			printSingleLineOption(pw, h, rb);
		}
		for (OptionHandler h : options) {
			printSingleLineOption(pw, h, rb);
		}
		pw.flush();
	}

	private void printSingleLineOption(PrintWriter pw, OptionHandler h, ResourceBundle rb) {
		pw.print(' ');
		if (!h.option.required())
			pw.print('[');
		pw.print(h.getNameAndMeta(rb));
		if (h.option.isMultiValued()) {
			pw.print(" ...");
		}
		if (!h.option.required())
			pw.print(']');
	}
}
