package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionDef;

import java.util.AbstractList;
import java.util.ResourceBundle;

/**
 * {@link OptionHandler} used with {@link Argument} for parsing typical "sub-command" pattern.
 *
 * <p>
 * The "sub-command" pattern refers to the design of the command line like <tt>git</tt> and <tt>svn</tt>, where
 * the first argument to the command designates a sub-command (say <code>git checkout</code>), then everything
 * that follows afterward are parsed by this sub-command (which is usually different depending on
 * which sub-command was selected.)
 *
 * <p>
 * This {@link OptionHandler} models this design pattern with the {@link SubCommands} annotation.
 * See the following example:
 *
 * <pre>{@code
 * class Git {
 *      &#64;Argument(handler={@link SubCommandHandler}.class)
 *      &#64;SubCommands({
 *          &#64;SubCommand(name="checkout", impl=CheckoutCommand.class),
 *          &#64;SubCommand(name="commit", impl=CommitCommand.class),
 *          ...
 *      })
 *      Command cmd;
 *
 *      &#64;Option(name="-r")
 *      boolean recursive;
 *
 *      public static void main(String[] args) {
 *          Git git = new Git();
 *          new CmdLineParser(git).parseArgument(args);
 *          git.cmd.execute();
 *      }
 * }
 *
 * class CheckoutCommand {
 *     &#64;Option(name="-a")
 *     boolean all;
 *
 *     ...
 * }
 * }</pre>
 *
 * <p>
 * An example of legal command line option for this is <code>-r checkout -a</code>.
 *
 * <ul>
 * <li>
 * {@link SubCommand} only works with {@link Argument} and not with {@link Option}.
 *
 * <li>
 * The same field/setter must be also annotated with {@link SubCommands} that specify possible sub-commands.
 *
 * <li>
 * Any {@link Option}s that you define in the <tt>Git</tt> class above can parse options that appear
 * prior to the sub-command name. This is useful for defining global options that work across sub-commands.
 *
 * <li>
 * The matching sub-command implementation gets instantiated with the default constructor,
 * then a new {@link CmdLineParser} will be created to parse its annotations.
 *
 * <li>
 * The rest of the arguments that follow the sub-command will be parsed with this new {@link CmdLineParser}
 *
 * <li>
 * The fully populated sub-command instance is set as the value.
 * </ul>
 *
 * <p>
 * This class defines a number of protected methods that allow subtypes to customize various parts of the
 * behaviours. This should also serve as an example if you want to combine this with more sophisticated
 * sub-command lookup, such as through META-INF/services, <a href="http://sezpoz.java.net/">sezpoz</a>,
 * or <a href="https://github.com/jenkinsci/lib-annotation-indexer">annotation indexer</a>.
 *
 * @author Kohsuke Kawaguchi
 */
public class SubCommandHandler extends OptionHandler<Object> {
    private final SubCommands commands;

    public SubCommandHandler(CmdLineParser parser, OptionDef option, Setter<Object> setter) {
        super(parser, option, setter);
        commands = setter.asAnnotatedElement().getAnnotation(SubCommands.class);
        if (commands==null)
            throw new IllegalStateException("SubCommandHandler must be used with @SubCommands annotation");
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        String subCmd = params.getParameter(0);

        for (SubCommand c : commands.value()) {
            if (c.name().equals(subCmd)) {
                setter.addValue(subCommand(c,params));
                return params.size();   // consume all the remaining tokens
            }
        }

        return fallback(subCmd);
    }

    protected int fallback(String subCmd) throws CmdLineException {
        throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, option.toString(), subCmd);
    }

    protected Object subCommand(SubCommand c, final Parameters params) throws CmdLineException {
        Object subCmd = instantiate(c);
        CmdLineParser p = configureParser(subCmd,c);
        p.parseArgument(new AbstractList<String>() {
            @Override
            public String get(int index) {
                try {
                    return params.getParameter(index+1);
                } catch (CmdLineException e) {
                    // invalid index was accessed.
                    throw new IndexOutOfBoundsException();
                }
            }

            @Override
            public int size() {
                return params.size()-1;
            }
        });
        return subCmd;
    }

    protected CmdLineParser configureParser(Object subCmd, SubCommand c) {
        return new CmdLineParser(subCmd);
    }

    protected Object instantiate(SubCommand c) {
        try {
            return c.impl().newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Failed to instantiate "+c,e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to instantiate "+c,e);
        }
    }

    @Override
    public String getDefaultMetaVariable() {
        StringBuffer rv = new StringBuffer();
        rv.append("[");
        for (SubCommand sc : commands.value()) {
            rv.append(sc.name()).append(" | ");
        }
        rv.delete(rv.length()-3, rv.length());
        rv.append("]");
        return rv.toString();
    }

    @Override
    public String getMetaVariable(ResourceBundle rb) {
        return getDefaultMetaVariable();
    }
}
