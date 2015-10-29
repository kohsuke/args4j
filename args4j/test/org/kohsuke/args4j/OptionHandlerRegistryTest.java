package org.kohsuke.args4j;

import junit.framework.TestCase;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

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
    
    /** Tests whether OptionHandlerRegistry.getRegistry() returns an instance. */
    public void testGetRegistry() {
        OptionHandlerRegistry instance = OptionHandlerRegistry.getRegistry();
        assertNotNull(instance);
    }
    
    /** Tests whether OptionHandlerRegistry.getRegistry() is a real singleton. */
    public void testGetRegistryWithSingleton() {
        OptionHandlerRegistry instance1 = OptionHandlerRegistry.getRegistry();
        OptionHandlerRegistry instance2 = OptionHandlerRegistry.getRegistry();
        OptionHandlerRegistry instance3 = OptionHandlerRegistry.getRegistry();
        
        assertSame(instance1, instance2);
        assertSame(instance1, instance3);
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
        OptionHandlerRegistry.getRegistry().registerHandler(CustomType.class,
                CustomTypeOptionHandler.class);
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-foo", "5");
        
        assertEquals(5, bean.foo.value);
    }

    public void testOptionHandlerFactory() throws CmdLineException {
        final int injected = 42;
        OptionHandlerRegistry.getRegistry().registerHandler(
                OhfTestType.class,
                new OptionHandlerRegistry.OptionHandlerFactory() {
                    public OptionHandler<?> getHandler(CmdLineParser parser, OptionDef o, Setter setter) {
                        return new OptionHandler<OhfTestType>(parser, o, setter) {
                            @Override
                            public int parseArguments(Parameters params) throws CmdLineException {
                                OhfTestType value = new OhfTestType();
                                value.value = injected;
                                setter.addValue(value);
                                return 1;
                            }

                            @Override
                            public String getDefaultMetaVariable() {
                                return "OHF_TEST_BEAN";
                            }
                        };
                    }
                }
        );

        OhfTestBean bean = new OhfTestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("something");

        assertEquals(injected, bean.value.value);
    }
    public static class OhfTestType {
        public int value = 0;
    }
    public static class OhfTestBean {
        @Argument
        private OhfTestType value;
    }
}
