package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.IllegalAnnotationError;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * Boolean {@link OptionHandler} that allows values to be specified as
 * --<option> and negated with --no<option>.
 *
 * Requires the caller to explicitly add the --no version to the alternative
 * names in the option definition, e.g.
 *   <code>
 *     name = "--option", aliases = { "--nooption" },
 *   </code>
 * If the negated option isn't present, the constructor will throw an IllegalAnnotationError.
 *
 * @Author Robin Garner
 */
public class NegatableBooleanOptionHandler extends OptionHandler<Boolean> {

  private final String negatedName;

  public NegatableBooleanOptionHandler(CmdLineParser parser,
      OptionDef option, Setter<? super Boolean> setter) {
    super(parser, option, setter);

    if (!(option instanceof NamedOptionDef)) {
      throw new IllegalAnnotationError("NegatableBooleanOptionHandler can only be used for named options.");
    }

    NamedOptionDef namedOption = (NamedOptionDef) option;
    this.negatedName = namedOption.name().replaceFirst("(-*)", "$1no");

    if (!find(negatedName, namedOption.aliases())) {
      throw new IllegalAnnotationError("There is no negated version of "+namedOption.name()+" in the option aliases.");
    }
  }

  @Override
  public int parseArguments(Parameters params) throws CmdLineException {
    if (params.getParameter(-1).equals(negatedName)) {
      setter.addValue(false);
    } else {
      setter.addValue(true);
    }
    return 0;
  }

  @Override
  public String getDefaultMetaVariable() {
    return null;
  }

  /**
   * Look for a string in an array of strings
   * @param str String to look for
   * @param strings Array of strings to search
   * @return {@code true} if {@code str} is present in {@code strings}
   */
  private static boolean find(String str, String[] strings) {
    for (String alias : strings) {
      if (alias.equals(str)) {
        return true;
      }
      System.err.println(alias + " != " + str);
    }
    return false;
  }

}
