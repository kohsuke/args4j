package org.kohsuke.args4j;

@SuppressWarnings("unused")
public class KeyValue {

  @Option(name="-s", aliases="--string")
  public String _string;

  @Option(name="-d", aliases="--double")
  public double _double;

}
