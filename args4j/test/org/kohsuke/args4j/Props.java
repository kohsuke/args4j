package org.kohsuke.args4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Test object for setting name-value pairs.
 * @author Jan Materne
 * @since 2.0.9
 */
public class Props {

    @Option(name="-T",usage="sets a key-value-pair")
    public Map<String,String> props = new HashMap<String, String>();

}