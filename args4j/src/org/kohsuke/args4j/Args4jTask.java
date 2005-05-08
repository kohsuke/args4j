package org.kohsuke.args4j;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;
import org.apache.tools.ant.Task;
import org.kohsuke.CmdLineException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Ant {@link Task} implementation that lets args4j
 * parse the values.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public abstract class Args4jTask extends Task implements DynamicConfigurator {
    
    private CmdLineParser parser;
    
    private final Map elementParsers = new HashMap();
    
    /**
     * Creates a configured {@link CmdLineParser}.
     * 
     * <p>
     * This method is called once right before Ant starts to
     * configure the task. The created {@link CmdLineParser} 
     * will be used to process the attribute/elements of the task.
     */
    protected abstract CmdLineParser createCmdLineParser() throws BuildException;
    
    /**
     * Obtains the {@link CmdLineParser} object used by this instance.
     */
    protected final CmdLineParser getParser() throws BuildException {
        if(parser==null)    parser=createCmdLineParser();
        return parser;
    }
    
    public Object createDynamicElement(String name) throws BuildException {
        try {
            AntElementParser parser = getParser().createAntElementParser(project,name);
            if(parser==null)    return null;
            
            Object p = parser.getParser();
            elementParsers.put( p, parser );
            return p;
        } catch( CmdLineException e ) {
            throw new BuildException(e);
        }
    }

    public void setDynamicAttribute(String name, String value) throws BuildException {
        try {
            getParser().setAntAttribute(project,name,value);
        } catch( CmdLineException e ) {
            throw new BuildException(e);
        }
    }
    
    public final void execute() throws BuildException {
        for (Iterator itr = elementParsers.entrySet().iterator();
            itr.hasNext();
            ) {
            Map.Entry e = (Map.Entry) itr.next();
            ((AntElementParser) e.getValue()).onComplete(e.getKey());
        }
        try {
            doExecute();
        } catch( CmdLineException e ) {
            throw new BuildException(e);
        }
    }
    
    /**
     * Performs the work.
     * 
     * @see Task#execute()
     */
    protected abstract void doExecute() throws BuildException,CmdLineException;
    
    /**
     * Parses the given arguments with the {@link CmdLineParser}
     * created by the {@link #createCmdLineParser()} method,
     * then calls the {@link #doExecute()} method.
     */
    public final void doMain( String[] args ) throws CmdLineException {
        getParser().parse(args);
        doExecute();
    }
}
