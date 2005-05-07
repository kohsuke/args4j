package org.kohsuke.args4j;

/**
 * Partial implementation of {@link CmdLineOption.Parameters}.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public abstract class AbstractParametersImpl implements CmdLineOption.Parameters {
    public int getIntParameter(int idx) throws CmdLineException {
        String token = getParameter(idx);
        try {
            return Integer.parseInt(token);
        } catch( NumberFormatException e ) {
            throw new IllegalOptionParameterException(getOptionName(),token);
        }
    }
    public double getDoubleParameter(int idx) throws CmdLineException {
        String token = getParameter(idx);
        try {
            return Double.parseDouble(token);
        } catch( NumberFormatException e ) {
            throw new IllegalOptionParameterException(getOptionName(),token);
        }
    }
}
