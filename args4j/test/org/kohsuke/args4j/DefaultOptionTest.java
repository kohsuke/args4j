package org.kohsuke.args4j;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Tests the default option value outputs in many ways.
 * @author Stephan Fuhrmann
 */
public class DefaultOptionTest extends Args4JTestBase<DefaultOption> {
    @Override
    public DefaultOption getTestObject() {
        return new DefaultOption();
    }
    
    private String findContaining(String searchString, String lines[]) {
        for (String line : lines) {
            if (line.contains(searchString))
                return line;
        }
        throw new NoSuchElementException("Could not find "+searchString);
    }

    public void testParseArgumentWithEmptyArgs() throws IOException, CmdLineException {
        
        String usageMessage[] = getUsageMessage();
        int a = 1;
    }
    
}
