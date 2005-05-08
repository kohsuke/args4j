package org.kohsuke.args4j;

import org.kohsuke.CmdLineException;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Command line argument owner.
 * 
 * @author
 *     Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public final class CmdLineParser {
    /**
     * Set of registered {@link CmdLineOption}s.
     */
    private final List options = new ArrayList(50);
    
    /**
     * Registered {@link Argument}s.
     */
    private final List arguments = new ArrayList(50);
    
    /**
     * Current index in {@link #arguments}.
     */
    private int argIdx=0;
    
    /**
     * Adds a new option to the owner.
     * 
     * @return
     *      The value passed as the <code>opt</code> parameter.
     */
    public CmdLineOption addOption( CmdLineOption opt ) {
        this.options.add(opt);
        return opt;
    }
    
    /**
     * Adds new argument owner.
     * 
     * @return
     *      The value passed as the <code>arg</code> parameter.
     */
    public Argument addArgument( Argument arg ) {
        this.arguments.add(arg);
        return arg;
    }
    
    /**
     * Adds all the {@link CmdLineOption}-derived fields on
     * this object.
     * 
     * <p>
     * This method uses Java reflection to the specified object,
     * and looks for fields whose types derive from {@link CmdLineOption}.
     * All such fields are added through {@link #addOption(CmdLineOption)}
     * method.
     * 
     * <p>
     * This method would be convenient if you have a class that
     * defines a bunch of {@link CmdLineOption}s as its fields.
     * For example,
     * 
     * <pre>
     * class MyMain {
     *     private {@link org.kohsuke.args4j.opts.BooleanCmdLineItem} opt1 = ...;
     *     private {@link org.kohsuke.args4j.opts.StringCmdLineItem}  opt2 = ...;
     *     private {@link org.kohsuke.args4j.opts.IntCmdLineItem}     opt3 = ...;
     *     ...
     * 
     *     void doMain( {@link String}[] args ) {
     *         {@link CmdLineParser} owner = new {@link CmdLineParser}();
     *         owner.addOptionClass(this);
     * 
     *         owner.parse(args);
     * 
     *         ....
     *     }
     * }
     * </pre>
     * 
     * @throws IllegalArgumentException
     *      If the specified object doesn't contain any 
     *      {@link CmdLineOption} fields. Given the typical
     *      use case, this is more likely to be a bug of the
     *      caller, but I appreciate your input on this behavior.
     */
    public void addOptionClass( Object obj ) {
        boolean added = false;
        
        for( Class c = obj.getClass(); c!=null; c=c.getSuperclass() ) {
            Field[] fields = c.getDeclaredFields();
            
            // make them accessible
            Field.setAccessible(fields,true);
            
            for( int i=0; i<fields.length; i++ ) {
                if( CmdLineOption.class.isAssignableFrom(fields[i].getType()) )
                    try {
                        // a CmdLineOption field
                        CmdLineOption f = (CmdLineOption) fields[i].get(obj);
                        if( f instanceof Argument && !f.getName().startsWith("-") )
                            // if it's also an argument and the name suggests that it is an argument,
                            // don't add it.
                            ;
                        else
                            addOption( f );
                        added = true;
                    } catch (IllegalArgumentException e) {
                        // can't happen
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // can't happen, too.
                        e.printStackTrace();
                    }
            }
        }
        
        if( !added )
            throw new IllegalArgumentException(
                "the specified class "+obj.getClass().getName()+" doesn't have any option field");
    }
    
    /**
     * Parse the arguments.
     * 
     * Can be invoked multiple times or recursively if necessary.
     */
    public void parse( String[] args ) throws CmdLineException {
        CmdLineImpl cmdLine = new CmdLineImpl(args);
        
        while( cmdLine.hasMore() ) {
            String arg = cmdLine.getOptionName();
            if( isOption(arg) & arg.length()>1 ) {
                // parse this as an option.
                int j;
                for( j=0; j<options.size(); j++ ) {
                    CmdLineOption opt = (CmdLineOption)options.get(j);
                    if( opt.getName().equals(arg) ) {
                        int diff = opt.parseArguments(this,cmdLine);
                        cmdLine.proceed(diff+1);
                        break;
                    }
                }
                if( j==options.size() )
                    throw new UndefinedOptionException(arg);
            } else {
                // parse this as arguments
                Argument a = (Argument)arguments.get(argIdx);
                if(argIdx!=arguments.size()-1)    argIdx++;
                a.addArgument(this,arg);
                cmdLine.proceed(1);
            }
        }
    }
    
    /**
     * Prints the usage screen.
     */
    public void printUsage( String programName, PrintWriter o ) {
        StringBuffer args = new StringBuffer();
        
        if(options.size()!=0) {
            args.append(' ');
            args.append(Messages.format("CmdLineParser.OptionsInArg"));
        }
            
        for( int i=0; i<arguments.size(); i++ ) {
            Argument arg = (Argument)arguments.get(i);
            args.append(" <");
            args.append(arg.getName());
            args.append(">");
            if(arg.acceptsMultiValues())
                args.append(" ...");
        }
        o.println(Messages.format("CmdLineParser.Usage",programName,args));

        int width = Math.max(getWidth(arguments),getWidth(options))+1;
        
        if(arguments.size()!=0) {
            o.println(Messages.format("CmdLineParser.Arguments"));
            for( int i=0; i<arguments.size(); i++ ) {
                Argument arg = (Argument)arguments.get(i);
                o.print("  "+arg.getName());
                for(int j=width-arg.getName().length();j>0;j--)  o.print(' ');
                o.print(':');
                o.println(arg.getDescription());
            }
        }
        
        if(options.size()!=0) {
            o.println(Messages.format("CmdLineParser.Options"));
            for( int i=0; i<options.size(); i++ ) {
                CmdLineOption opt = (CmdLineOption)options.get(i);
                o.print("  "+opt.getName());
                for(int j=width-opt.getName().length();j>0;j--)  o.print(' ');
                o.print(':');
                o.println(opt.getDescription());
            }
        }
        
        o.flush();
    }
    
    private int getWidth(List lst) {
        int w=0;
        for( int i=0; i<lst.size(); i++ ) {
            CmdLineItem item = (CmdLineItem)lst.get(i);
            w = Math.max(w,item.getName().length());
        }
        return w;
    }
    
    
    
    /**
     * Returns an {@link Iterator} that walks over
     * both arguments and options.
     */
    protected Iterator iterateCmdLineItems() {
        return new SequentialIterator(arguments.iterator(),options.iterator());
    }
    
//    /**
//     * Gets the usage message generated from registered options.
//     * 
//     * @return
//     *      non-null valid string that ends with '\n' and that
//     *      doesn't begin with '\n' (or "" if no option is registered.)
//     */
//    public final String getUsage() {
//        StringBuffer buf = new StringBuffer();
//        for( int i=0; i<options.size(); i++ )
//            ((CmdLineOption)options.get(i)).appendUsage(buf);
//        return buf.toString();
//    }
//    
//    /**
//     * Prints the usage messages.
//     * 
//     * Just a convenience method for <code>out.print(getUsage())</code>.
//     */
//    public final void printUsage( PrintStream out ) {
//        out.print(getUsage());
//    }
    
    /**
     * Returns true if the given token is an option
     * (as opposed to an argument.)
     */
    private boolean isOption(String arg) {
        return arg.startsWith("-");
    }

    /**
     * Essentially a pointer over a {@link String} array.
     * Can move forward, can look ahead.
     */
    private class CmdLineImpl extends AbstractParametersImpl {
        private final String[] args;
        private int pos;
        
        CmdLineImpl( String[] args ) {
            this.args = args;
            pos = 0;
        }
        
        private boolean hasMore() {
            return pos<args.length;
        }

        private String getCurrentToken() {
            return args[pos];
        }
        
        private void proceed( int n ) {
            pos += n;
        }
        

        public String getOptionName() {
            return getCurrentToken();
        }

        public String getParameter(int idx) throws CmdLineException {
            if( pos+idx+1>=args.length )
                throw new MissingOptionParameterException(getOptionName());
            return args[pos+idx+1];
        }
    }

    /**
     * 
     * <p>
     * This method is called only from {@link Args4jTask}.
     * 
     * @param antProject
     *      {@link org.apache.tools.ant.Project} object.
     * @param name
     *      element name.
     * @return
     *      null if the element is not recognized.
     */
    protected AntElementParser createAntElementParser(Object antProject,String name) throws CmdLineException {
        Iterator itr = iterateCmdLineItems();
        while(itr.hasNext()) {
            CmdLineItem cli = (CmdLineItem)itr.next();
            AntElementParser p = cli.createAntElementParser(this,antProject,name);
            if(p!=null)     return p;
        }
        return null;
    }

    /**
     * @param name
     *      attribute name
     * @param antProject
     *      {@link org.apache.tools.ant.Project} object.
     * @param value
     *      attribute value
     */
    public void setAntAttribute(Object antProject, String name, String value) throws CmdLineException {
        Iterator itr = iterateCmdLineItems();
        while(itr.hasNext()) {
            CmdLineItem cli = (CmdLineItem)itr.next();
            if(cli.parseAntAttribute(this,antProject,name,value))
                return; // processed
        }
        throw new CmdLineException(Messages.format("UndefinedAttribute",name));
    }
    
}
