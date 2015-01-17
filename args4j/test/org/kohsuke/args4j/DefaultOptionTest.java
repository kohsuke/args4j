package org.kohsuke.args4j;

import java.io.IOException;

/**
 * Tests the default option value outputs in many ways.
 * @author Stephan Fuhrmann
 */
public class DefaultOptionTest extends Args4JTestBase<DefaultOption> {
    
    @Override
    public DefaultOption getTestObject() {
        return new DefaultOption();
    }
    
    public void testParseArgumentWithEmptyArgs() throws IOException, CmdLineException {
        
        String usageMessage[] = getUsageMessage();
        
        String testMessageExpected[] = new String[] {
        " -byteVal N                             : my favorite byte (default: 0)",
        " -drink [BEER | WHISKEY | SCOTCH |      : my favorite drink (default: BEER)",
        " BOURBON | BRANDY]                         ",
        " -drinkArray [BEER | WHISKEY | SCOTCH   : my favorite drinks (default: [BEER,",
        " | BOURBON | BRANDY]                      BOURBON])",
        " -req VAL                               : set a string",
        " -str VAL                               : set a string (default: pretty string)",
        " -strArray VAL                          : my favorite strarr (default: [san,",
        "                                          dra, chen])"
        };

        for (int i=0; i < usageMessage.length; i++) {
            assertEquals("Line "+(i+1)+" wrong", testMessageExpected[i], usageMessage[i]);
        }
    }
    
}
