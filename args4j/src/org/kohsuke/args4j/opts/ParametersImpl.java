package org.kohsuke.args4j.opts;

import org.kohsuke.args4j.AbstractParametersImpl;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.MissingOptionParameterException;

/**
 * {@link CmdLineOption.Parameters} implementation backed by
 * String array.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
class ParametersImpl extends AbstractParametersImpl {

    private final String[] values;
    
    ParametersImpl( String[] values ) {
        this.values = values;
    }
    
    public String getOptionName() {
        return values[0];
    }

    public String getParameter(int idx) throws CmdLineException {
        try {
            return values[idx+1];
        } catch( ArrayIndexOutOfBoundsException e ) {
            throw new MissingOptionParameterException(getOptionName());
        }
    }
}
