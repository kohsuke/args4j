package org.kohsuke.args4j;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.kohsuke.args4j.spi.OptionHandler;

/**
 * Displays command line help in various ways.
 * Is created using {@link CmdLineParser#createCmdLineHelp() }.
 * @author Stephan Fuhrmann
 */
public class CmdLineHelp {
    private final CmdLineParser cmdLineParser;
    private final ParserProperties props;

    /** Is created using {@link CmdLineParser#createCmdLineHelp() }.
     */
    CmdLineHelp(CmdLineParser cmdLineParser, ParserProperties props) {
        this.cmdLineParser = cmdLineParser;
        this.props = props;
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
        return printExample(filter,null);
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
     */
    public String printExample(OptionHandlerFilter mode, ResourceBundle rb) {
        StringBuilder buf = new StringBuilder();

        if (mode == null) {
            throw new NullPointerException("mode is null");
        }
        
        for (OptionHandler h : cmdLineParser.getOptions()) {
            OptionDef option = h.option;
            if(option.usage().length()==0)  continue;   // ignore
            if(!mode.select(h))             continue;

            buf.append(' ');
            buf.append(h.getNameAndMeta(rb, props));
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
        for (OptionHandler h : cmdLineParser.getArguments()) {
            int curLen = getPrefixLen(h, rb);
            len = Math.max(len,curLen);
        }
        for (OptionHandler h: cmdLineParser.getOptions()) {
            int curLen = getPrefixLen(h, rb);
            len = Math.max(len,curLen);
        }

        // then print
        for (OptionHandler h : cmdLineParser.getArguments()) {
        	printOption(w, h, len, rb, filter);
        }
        for (OptionHandler h : cmdLineParser.getOptions()) {
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
        int totalUsageWidth = props.getUsageWidth();
    	int widthMetadata = Math.min(len, (totalUsageWidth - 4) / 2);
    	int widthUsage    = totalUsageWidth - 4 - widthMetadata;

    	// Line wrapping
    	List<String> namesAndMetas = wrapLines(handler.getNameAndMeta(rb, props), widthMetadata);
    	List<String> usages        = wrapLines(localize(handler.option.usage(),rb), widthUsage);

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

            return h.getNameAndMeta(rb, props).length();
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
        if (out == null)
            throw new NullPointerException("OutputStream is null");        
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
        if (w == null)
            throw new NullPointerException("Writer is null");
        
		PrintWriter pw = new PrintWriter(w);
		for (OptionHandler h : cmdLineParser.getArguments()) {
			printSingleLineOption(pw, h, rb);
		}
		for (OptionHandler h : cmdLineParser.getOptions()) {
			printSingleLineOption(pw, h, rb);
		}
		pw.flush();
	}

	private void printSingleLineOption(PrintWriter pw, OptionHandler h, ResourceBundle rb) {
		pw.print(' ');
		if (!h.option.required())
			pw.print('[');
		pw.print(h.getNameAndMeta(rb, props));
		if (h.option.isMultiValued()) {
			pw.print(" ...");
		}
		if (!h.option.required())
			pw.print(']');
	}

}
