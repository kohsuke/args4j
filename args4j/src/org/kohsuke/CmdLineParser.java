package org.kohsuke;




/**
 * Command line argument owner.
 *
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class CmdLineParser {
    /**
     * Option instance.
     */
    private final Object option;

    /**
     * Creates a new command line owner that
     * parses arguments/options and set them into
     * the given object.
     *
     * @param option
     *      instance of a class annotated by {@link Option} and {@link Argument}.
     *      this object will receive values.
     */
    public CmdLineParser(Object option) {
        this.option = option;
    }


}
