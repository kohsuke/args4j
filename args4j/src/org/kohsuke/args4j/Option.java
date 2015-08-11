package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Setter;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.util.ResourceBundle;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a field/setter that receives a command line switch value.
 *
 * <p>
 * This annotation can be placed on a field of type T or the method
 * of the form <code>void <i><code>methodName</code></i>(T value)</code>. Its access
 * modified can be anything, but if it's not public, your application
 * needs to run in a security context that allows args4j to access
 * the field/method (see {@link AccessibleObject#setAccessible(boolean)}.
 *
 * <p>
 * The behavior of the annotation differs depending on T --- the type
 * of the field or the parameter of the method.
 *
 * <h2>Boolean Switch</h2>
 * <p>
 * When <var>T</var> is {@code boolean} , it represents
 * a {@code boolean} option that takes the form of <code>-OPT</code>. When this option is set,
 * the property will be set to {@code true}.
 *
 * <h2>String Switch</h2>
 * <p>
 * When <var>T</var> is {@link String}, it represents
 * an option that takes one operand. The value of the operand is set
 * to the property.
 *
 * <h2>Enum Switch</h2>
 * <p>
 * When <var>T</var> is derived from {@link Enum}, it represents an option that takes
 * an operand, which must be one of the enum constant. The comparion between
 * the operand and the enum constant name is done in a case insensitive fashion.
 * <p>
 * For example, the following definition will represent command line options
 * like <code>-coin penny</code> or <code>-coin DIME</code>,
 * but things like <code>-coin</code> or <code>-coin abc</code> are errors.
 *
 * <pre>
 * enum Coin { PENNY,NICKEL,DIME,QUARTER }
 *
 * class Option {
 *   &#64;Option(name="-coin")
 *   public Coin coin;
 * }
 * </pre>
 *
 * <h2>File Switch</h2>
 * <p>
 * When <var>T</var> is a {@link File}, it represents an option that takes a file/directory
 * name as an operand.
 *
 * @author Kohsuke Kawaguchi
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD,PARAMETER})
public @interface Option {
    /**
     * Name of the option, such as <code>-foo</code> or <code>-bar</code>.
     */
    String name();
    
    /**
     * Aliases for the options, such as <code>--long-option-name</code>.
     */
    String[] aliases() default { };

    /**
     * Help string used to display the usage screen.
     *
     * <p>
     * This parameter works in two ways. For a simple use,
     * you can just encode the human-readable help string directly,
     * and that will be used as the message. This is easier,
     * but it doesn't support localization.
     *
     * <p>
     * For more advanced use, this property is set to a key of a
     * {@link ResourceBundle}. The actual message is obtained
     * by querying a {@link ResourceBundle} instance supplied to
     * {@link CmdLineParser} by this key. This allows the usage
     * screen to be properly localized.
     *
     * <p>
     * If this value is empty, the option will not be displayed
     * in the usage screen.
     */
    String usage() default "";

    /**
     * When the option takes an operand, the usage screen will show something like this
     *
     * <pre>
     * -x FOO  : blah blah blah
     * </pre>
     *
     * You can replace the <code>FOO</code> token by using this parameter.
     *
     * <p>
     * If left unspecified, this value is infered from the type of the option.
     *
     * <p>
     * Just like {@link #usage()}, normally, this value is printed as is.
     * But if a {@link ResourceBundle} is given to the {@link CmdLineParser},
     * it will be used to obtain the locale-specific value.
     */
    String metaVar() default "";

    /**
     * Specify that the option is mandatory.
     *
     * <p>
     * At the end of {@link CmdLineParser#parseArgument(String...)},
     * a {@link CmdLineException} will be thrown if a required option
     * is not present.
     *
     * <p>
     * Note that in most of the command line interface design principles,
     * options should be really optional. So use caution when using this
     * flag.
     */
    boolean required() default false;
    
    /**
     * Specify that the option is a help option.
     *
     * <p>
     * When flagging an option being the help option, required
     * arguments or options that are missing in an actual command
     * line don't cause an exception to be thrown.
     * @see #required() 
     */
    boolean help() default false;

    /**
     * Specify that the option is hidden from the usage, by default.
     *
     * <p>
     * You can still have {@link CmdLineParser} show hidden options
     * by using {@link OptionHandlerFilter#ALL}, which allows you to
     * create an option that shows hidden options.
     *
     * <p>
     * If you need more complicated filtering logic, define your own
     * annotations and check them in {@link Setter#asAnnotatedElement()}.
     *
     * @see OptionHandlerFilter#PUBLIC
     */
    boolean hidden() default false;

    /**
     * Specify the {@link OptionHandler} that processes the command line arguments.
     *
     * <p>
     * The default value {@link OptionHandler} indicates that
     * the {@link OptionHandler} will be infered from
     * the type of the field/method where a {@link Option} annotation
     * is placed.
     *
     * <p>
     * If this annotation element is used, it overrides the inference
     * and determines the handler to be used. This is convenient for
     * defining a non-standard option parsing semantics.
     *
     * <h3>Example</h3>
     *
     * <pre>
     * // this is a normal "-r" option
     * &#64;Option(name="-r")
     * boolean value;
     *
     * // this causes arg4j to use MyHandler, not the default
     * // handler provided for boolean
     * &#64;Option(name="-b",handler=MyHandler.class)
     * boolean value;
     * </pre>
     */
    Class<? extends OptionHandler> handler() default OptionHandler.class;

    /**
     * List of other options that this option depends on.
     *
     * <h3>Example</h3>
     *
     * <pre>
     *  &#64;Option(name="-a")
     *  int a;
     *  //-b is not required but if it's provided, then a becomes required
     *  &#64;Option(name="-b", depends={"-a"})
     *  int b;
     * </pre>
     *
     * <p>
     * At the end of {@link CmdLineParser#parseArgument(String...)},
     * a {@link CmdLineException} will be thrown if options required by another one
     * are not present.
     * </p>
     */
    String[] depends() default { };
    
    /**
     * List of other options that this option is incompatible with..
     *
     * <h3>Example</h3>
     *
     * <pre>
     *  &#64;Option(name="-a")
     *  int a;
     *  // -h and -a cannot be specified together
     *  &#64;Option(name="-h", forbids={"-a"})
     *  boolean h;
     * </pre>
     *
     * <p>
     * At the end of {@link CmdLineParser#parseArgument(String...)},
     * a {@link CmdLineException} will be thrown if forbidden option
     * combinations are present.
     * </p>
     */
    String[] forbids() default { };    
}
