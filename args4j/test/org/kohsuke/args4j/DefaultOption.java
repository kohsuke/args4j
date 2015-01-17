package org.kohsuke.args4j;

@SuppressWarnings("unused")
public class DefaultOption {
    @Option(name="-str",usage="set a string")
    public String str = "pretty string";
    
    @Option(name="-req",usage="set a string", required = true)
    public String req = "required";
    
    @Option(name="-noDefault")
    public String noDefault;
    
    @Option(name="-noDefaultReq", required = true)
    public String noDefaultReq;
    
    @Option(name="-byteVal", usage = "my favorite byte")
    public byte byteVal;
    
    @Option(name="-strArray")
    public String strArray[] = new String[] { "san", "dra", "chen"};
    
    public enum DrinkName {
        BEER,
        WHISKEY,
        SCOTCH,
        BOURBON,
        BRANDY
    };
    
    @Option(name="-drinkArray")
    public DrinkName drinkArray[] = new DrinkName[] { DrinkName.BEER, DrinkName.BOURBON };
    
    @Option(name="-drink")
    public DrinkName drink = DrinkName.BEER;
    
    @Argument
    public String arguments[] = new String[] { "foo", "bar" };
}
