package org.kohsuke.args4j;

import junit.framework.TestCase;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;

public class OptionHandlerRegistryTest extends TestCase {
    
    public static class CustomTypeOptionHandler extends OneArgumentOptionHandler<CustomType> {
	public CustomTypeOptionHandler(CmdLineParser parser, OptionDef option, org.kohsuke.args4j.spi.Setter<? super CustomType> setter) {
		super(parser, option, setter);
	}

	@Override
	protected CustomType parse(String argument) throws NumberFormatException {
            CustomType result = new CustomType();
            result.value = Integer.parseInt(argument);
	    return result;
	}
}
    
    public static class CustomType {
        int value;
    }
    
    private static class TestBean {
        @Option(name = "-foo")
        private CustomType foo;
    }
    
    /** Tests whether unknown types raise an exception. */
    public void testCmdLineParserWithUnregistered() {
        try {        
            TestBean bean = new TestBean();
            CmdLineParser parser = new CmdLineParser(bean);
            assertTrue("Should throw an exception", true);
        }
        catch (IllegalAnnotationError e) {
            assertNotNull(e);
        }
    }
    
    /** Tests whether registering a handler works. */
    public void testCmdLineParserWithCustomRegistered() throws CmdLineException {
        TestBean bean = new TestBean();

        OptionHandlerRegistry registry = new OptionHandlerRegistry();
        registry.registerHandler(CustomType.class, CustomTypeOptionHandler.class);

        CmdLineParser parser = new CmdLineParser(bean, ParserProperties.defaults(), registry);
        parser.parseArgument("-foo", "5");
        
        assertEquals(5, bean.foo.value);
    }    
}
