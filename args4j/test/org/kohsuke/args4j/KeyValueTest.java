package org.kohsuke.args4j;

public class KeyValueTest extends Args4JTestBase<KeyValue> {

  @Override
  public KeyValue getTestObject() {
    return new KeyValue();
  }

  public void testDouble() throws CmdLineException {
    args = new String[]{"-double=42.54"};
    parser.parseArgument(args);
    assertEquals(42.54, testObject._double, 0);
  }

  public void testChar() throws CmdLineException {
    args = new String[]{"-string=stringValue"};
    parser.parseArgument(args);
    assertEquals("stringValue", testObject._string);
  }

}
