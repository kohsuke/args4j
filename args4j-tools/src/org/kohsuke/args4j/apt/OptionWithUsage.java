package org.kohsuke.args4j.apt;

import org.kohsuke.args4j.Option;

/**
 * @author Jerome Lacoste
*/
class OptionWithUsage {
    final Option option;
    final String usage;

    OptionWithUsage(Option option, String usage) {
        this.option = option;
        this.usage = usage;
    }

}
