package org.kohsuke.args4j;

public class KeyValueCustomDelimiterTest extends Args4JTestBase<KeyValue> {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    testObject = getTestObject();

    ParserProperties properties = ParserProperties.defaults();
    properties.withOptionValueDelimiter(":");

    parser = new CmdLineParser(testObject, properties);
  }

  @Override
  public KeyValue getTestObject() {
    return new KeyValue();
  }

  public void testDouble() throws CmdLineException {
    args = new String[]{"--double:42.54"};
    parser.parseArgument(args);
    assertEquals(42.54, testObject._double, 0);
  }

  public void testDoubleShort() throws CmdLineException {
    args = new String[]{"-d:42.54"};
    parser.parseArgument(args);
    assertEquals(42.54, testObject._double, 0);
  }


  public void testChar() throws CmdLineException {
    args = new String[]{"--string:stringValue"};
    parser.parseArgument(args);
    assertEquals("stringValue", testObject._string);
  }

  public void testCharShort() throws CmdLineException {
    args = new String[]{"-s:stringValue"};
    parser.parseArgument(args);
    assertEquals("stringValue", testObject._string);
  }

}
