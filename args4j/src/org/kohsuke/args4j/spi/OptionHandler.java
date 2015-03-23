package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.*;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Code that parses operands of an option into Java.
 *
 * <p>
 * This class can be extended by application to support additional Java datatypes in option operands.
 *
 * <p>
 * Implementation of this class needs to be registered to args4j by using
 * {@link OptionHandlerRegistry#registerHandler(Class,Class)}.
 * For registration to work, subclasses will need to implement the constructor
 * with the signature
 * {@link OptionHandler#OptionHandler(CmdLineParser, OptionDef, Setter)}.
 *
 * @param <T>
 *      The {@code component} type of the field that this {@link OptionHandler} works with.
 *      When I say "component", I mean a field that can hold multiple values
 *      (such as {@link Collection} or array). This should refer to its component time.
 *      
 *      {@link Setter} implementations abstract away multi-value-ness by allowing {@link OptionHandler}
 *      to invoke its {@link Setter#addValue(Object)} multiple times.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class OptionHandler<T> {
    /**
     * The annotation.
     */
    public final OptionDef option;
    /**
     * Object to be used for setting value.
     */
    public final Setter<? super T> setter;
    /**
     * The owner to which this handler belongs to.
     */
    public final CmdLineParser owner;

    protected OptionHandler(CmdLineParser parser, OptionDef option, Setter<? super T> setter) {
        this.owner = parser;
        this.option = option;
        this.setter = setter;
    }

    /**
     * Called if the option that this owner recognizes is found.
     *
     * @param params
     *      The rest of the arguments. This method can use this
     *      object to access the arguments of the option if necessary.
     *
     *      The object is valid only during the method call.
     *
     * @return
     *      The number of arguments consumed. (For example, returns {@code 0}
     *      if this option doesn't take any parameters.)
     */
    public abstract int parseArguments( Parameters params ) throws CmdLineException;

    /**
     * Gets the default meta variable name used to print the usage screen.
     * 
     * The value returned by this method can be a reference in the
     * {@code ResourceBundle}, if one was passed to
     * {@link CmdLineParser}.
     *
     * @return {@code null} to hide a meta variable.
     */
    public abstract String getDefaultMetaVariable();

    public String getMetaVariable(ResourceBundle rb) {
        String token = option.metaVar();
        if(token.length()==0)
            token = getDefaultMetaVariable();
        if(token==null) return null;

        if(rb!=null) {
            String localized = rb.getString(token);
            if(localized!=null)
                token = localized;
        }

        return token;
    }
    
    /**
     * Get string representing usage for this option, of the form "name metaval",
     * e.g. "-foo VALUE" or "--foo VALUE"
     * @param rb ResourceBundle to get localized version of meta string
     */
    public final String getNameAndMeta(ResourceBundle rb) {
        return getNameAndMeta(rb, ParserProperties.defaults());
    }

    /**
     * Get string representing usage for this option, of the form "name metaval" or "name=metaval,
     * e.g. "--foo VALUE" or "--foo=VALUE"
     * @param rb ResourceBundle to get localized version of meta string
     * @param properties
     *      Affects the formatting behaviours.
     */
    public final String getNameAndMeta(ResourceBundle rb, ParserProperties properties) {
    	String str = option.isArgument() ? "" : option.toString();
    	String meta = getMetaVariable(rb);
    	if (meta != null) {
    		if (str.length() > 0) {
    			str += properties.getOptionValueDelimiter();
    		}
    		str += meta;
    	}
    	return str;
    }

    /**
     * The opposite of the parse operation.
     *
     * This method is used to print the usage screen.
     */
    protected String print(T v) {
        return String.valueOf(v);
    }

    /**
     * Prints the default value by introspecting the current setter as {@link Getter}.
     *
     * @return null if the current value of the setter isn't available.
     */
    public String printDefaultValue() {
        if (setter instanceof Getter) {
            Getter getter = (Getter)setter;
            List<T> defaultValues = getter.getValueList();
            if (defaultValues != null && !defaultValues.isEmpty()) {
                StringBuilder buf = new StringBuilder();
                if (defaultValues.size()>1) {
                    for (T v : defaultValues) {
                        buf.append( buf.length()==0 ? '[' : ',' );
                        buf.append(print(v));
                    }
                    buf.append(']');
                } else {
                    buf.append(print(defaultValues.get(0)));
                }
                return buf.toString();
            }
        }
        return null;
    }
}
