package org.kohsuke.args4j;

// a 'custom' exception
import javax.management.InvalidAttributeValueException;


public class CustomExceptionTest extends Args4JTestBase<CustomExceptionTest> {

    private String errMsgX = "this is a usual CLI exception";
    private String errMsgY = "this is a 'custom' exception";


    @Option(name="-x")
    public void setX(String x) {
        throw new IllegalArgumentException(errMsgX);
    }
    
    @Option(name="-y")
    public void setY(String y) throws InvalidAttributeValueException {
        throw new InvalidAttributeValueException(errMsgX);
    }


    @Override
    public CustomExceptionTest getTestObject() {
        return this;
    }
    
    
    protected void tryThis(String expected, Class expectedExceptionClass, String... parserArgs) {
        String expMsg = expectedExceptionClass.getName() + ": " + expected;
        try {
            parser.parseArgument(parserArgs);
            fail("Exception expected.");
        } catch (RuntimeException e) {
            // RuntimeExceptions are passed through the parser to the caller.
            assertEquals("Lost exception message.", expMsg, e.toString());
        } catch (CmdLineException e) {
            // Other Exceptions are wrapped into a CLE so we must ensure not to loose the
            // message.
            assertEquals("Lost exception message.", expMsg, e.getMessage());
        } catch (Exception e) {
            fail("Wrong exception type thrown.");
        }
    }
    
    public void testRuntimeException() throws Exception {
        tryThis(errMsgX, IllegalArgumentException.class, "-x", "value");
    }
    
    public void testCustomException() throws Exception {
        tryThis(errMsgX, InvalidAttributeValueException.class, "-y", "value");
    }

}