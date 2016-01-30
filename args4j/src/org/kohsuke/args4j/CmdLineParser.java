package org.kohsuke.args4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import org.kohsuke.args4j.spi.Getter;

import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

import static org.kohsuke.args4j.Utilities.checkNonNull;

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

        if (parserProperties.getOptionSorter()!=null) {
            Collections.sort(options, parserProperties.getOptionSorter());
        }
    }

    public ParserProperties getProperties() {
        return parserProperties;
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
    protected OptionHandler createOptionHandler(OptionDef o, Setter setter) {
        checkNonNull(o, "OptionDef");
        checkNonNull(setter, "Setter");
        return OptionHandlerRegistry.getRegistry().createOptionHandler(this, o, setter);
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
     */
    public String printExample(OptionHandlerFilter filter) {
        return printExample(filter, null);
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
     * This method produces a string like <code> -d &lt;dir&gt; -v -b</code>.
     * This is useful for printing a command line example (perhaps
     * as a part of the usage screen).
     *
     *
     * @param mode
     *      Determines which options will be a part of the returned string.
     *      Must not be {@code null}.
     * @param rb
     *      If non-{@code null}, meta variables (<code>&lt;dir&gt;</code> in the above example)
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
     *      <code>System.err.println("java -jar my.jar"+parser.printExample(REQUIRED)+" arg1 arg2");</code>
     * @throws NullPointerException if {@code mode} is {@code null}.
     */
    public String printExample(OptionHandlerFilter mode, ResourceBundle rb) {
        StringBuilder buf = new StringBuilder();

        checkNonNull(mode, "mode");
        
        for (OptionHandler h : options) {
            OptionDef option = h.option;
            if(option.usage().length()==0)  continue;   // ignore
            if(!mode.select(h))             continue;

            buf.append(' ');
            buf.append(h.getNameAndMeta(rb, parserProperties));
        }

        return buf.toString();
    }

    /**
     * @deprecated
     *      Use {@link #printExample(OptionHandlerFilter,ResourceBundle)}
     */
    public String printExample(ExampleMode mode, ResourceBundle rb) {
        return printExample((OptionHandlerFilter) mode, rb);
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
     * Prints the list of all the non-hidden options and their usages to the screen.
     *
     * <p>
     * Short for {@code printUsage(out,rb,OptionHandlerFilter.PUBLIC)}
     */
    public void printUsage(Writer out, ResourceBundle rb) {
        printUsage(out, rb, OptionHandlerFilter.PUBLIC);
    }

    /**
     * Prints the list of all the non-hidden options and their usages to the screen.
     *
     * @param rb
     *      If non-{@code null}, {@link Option#usage()} is treated
     *      as a key to obtain the actual message from this resource bundle.
     * @param filter
     *      Controls which options to be printed.
     */
    public void printUsage(Writer out, ResourceBundle rb, OptionHandlerFilter filter) {
        PrintWriter w = new PrintWriter(out);
        // determine the length of the option + metavar first
        int len = 0;
        for (OptionHandler h : arguments) {
            int curLen = getPrefixLen(h, rb);
            len = Math.max(len,curLen);
        }
        for (OptionHandler h: options) {
            int curLen = getPrefixLen(h, rb);
            len = Math.max(len,curLen);
        }

        // then print
        for (OptionHandler h : arguments) {
        	printOption(w, h, len, rb, filter);
        }
        for (OptionHandler h : options) {
        	printOption(w, h, len, rb, filter);
        }

        w.flush();
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
     */
    protected void printOption(PrintWriter out, OptionHandler handler, int len, ResourceBundle rb, OptionHandlerFilter filter) {
    	// Hiding options without usage information
    	if (handler.option.usage() == null ||
            handler.option.usage().length() == 0 ||
            !filter.select(handler)) {
    		return;
    	}

    	// What is the width of the two data columns
        int totalUsageWidth = parserProperties.getUsageWidth();
    	int widthMetadata = Math.min(len, (totalUsageWidth - 4) / 2);
    	int widthUsage    = totalUsageWidth - 4 - widthMetadata;

        String defaultValuePart = createDefaultValuePart(handler);
        
    	// Line wrapping
        // the 'left' side
    	List<String> namesAndMetas = wrapLines(handler.getNameAndMeta(rb, parserProperties), widthMetadata);
        // the 'right' side
    	List<String> usages        = wrapLines(localize(handler.option.usage(),rb) + defaultValuePart, widthUsage);

    	// Output
    	for(int i=0; i<Math.max(namesAndMetas.size(), usages.size()); i++) {
    		String nameAndMeta = (i >= namesAndMetas.size()) ? "" : namesAndMetas.get(i);
			String usage       = (i >= usages.size())        ? "" : usages.get(i);
			String format      = ((nameAndMeta.length() > 0) && (i == 0))
			                   ? " %1$-" + widthMetadata + "s : %2$-1s"
			                   : " %1$-" + widthMetadata + "s   %2$-1s";
			String output = String.format(format, nameAndMeta, usage);
                                                
			out.println(output);
    	}
    }

    private String createDefaultValuePart(OptionHandler handler) {
        if (parserProperties.getShowDefaults() && !handler.option.required() && handler.setter instanceof Getter) {
            String v = handler.printDefaultValue();
            if (v!=null)
                return " " + Messages.DEFAULT_VALUE.format(v);
        }
        return "";
    }

    private String localize(String s, ResourceBundle rb) {
        if(rb!=null)    return rb.getString(s);
        return s;
    }

    /**
     * Wraps a line so that the resulting parts are not longer than a given maximum length.
     *
     * @param line       Line to wrap
     * @param maxLength  maximum length for the resulting parts
     * @return list of all wrapped parts
     */
    private List<String> wrapLines(String line, final int maxLength) {
    	List<String> rv = new ArrayList<String>();
        for (String restOfLine : line.split("\\n")) {
            while (restOfLine.length() > maxLength) {
                // try to wrap at space, but don't try too hard as some languages don't even have whitespaces.
                int lineLength;
                String candidate = restOfLine.substring(0, maxLength);
                int sp=candidate.lastIndexOf(' ');
                if(sp>maxLength*3/5)    lineLength=sp;
                else                    lineLength=maxLength;
                rv.add(restOfLine.substring(0, lineLength));
                restOfLine = restOfLine.substring(lineLength).trim();
            }
            rv.add(restOfLine);
        }
    	return rv;
    }

	private int getPrefixLen(OptionHandler h, ResourceBundle rb) {
		if(h.option.usage().length()==0)
			return 0;

		return h.getNameAndMeta(rb, parserProperties).length();
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
        
        String expandedArgs[] = args;
        if (parserProperties.getAtSyntax()) {
            expandedArgs = expandAtFiles(args);
        }
        CmdLineImpl cmdLine = new CmdLineImpl(expandedArgs);

        Set<OptionHandler> present = new HashSet<OptionHandler>();
        int argIndex = 0;

        while( cmdLine.hasMore() ) {
            String arg = cmdLine.getCurrentToken();
            if( isOption(arg) ) {
                // '=' is for historical compatibility fallback
                boolean isKeyValuePair = arg.contains(parserProperties.getOptionValueDelimiter()) || arg.indexOf('=')!=-1;

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
                throw new CmdLineException(this, Messages.FORBIDDEN_OPTION_PRESENT,
                        handler.option.toString(), Arrays.toString(((NamedOptionDef) handler.option).forbids()));
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
        // Look for key/value pair first.
        int pos = name.indexOf(parserProperties.getOptionValueDelimiter());
        if (pos < 0) {
            pos = name.indexOf('=');    // historical compatibility fallback
        }
        if (pos > 0) {
            name = name.substring(0, pos);
        }
		return findOptionByName(name);
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
     * @deprecated You should use {@link OptionHandlerRegistry#registerHandler(java.lang.Class, java.lang.Class)} instead.
     */
    public static void registerHandler( Class valueType, Class<? extends OptionHandler> handlerClass ) {
        checkNonNull(valueType, "valueType");
        checkNonNull(handlerClass, "handlerClass");

        OptionHandlerRegistry.getRegistry().registerHandler(valueType, handlerClass);
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
		pw.print(h.getNameAndMeta(rb, parserProperties));
		if (h.option.isMultiValued()) {
			pw.print(" ...");
		}
		if (!h.option.required())
			pw.print(']');
	}
}
