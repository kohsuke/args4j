import java.io.File;

import org.kohsuke.args4j.Option;

/**
 * Show the use of the args4j Starter class. 
 * @author Jan Materne
 * @see SampleStarter.bat
 */
public class SampleStarter {

	@Option(name = "-name",usage="Sets the name")
	public String name;

	@Option(name = "-file",usage="Sets the file")
	public File file;

	/* 
	 * Just do something ...
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SampleStarter:");
		buffer.append(" name='");
		buffer.append(name);
		buffer.append("'  file='");
		buffer.append( (file!=null) ? file.getAbsoluteFile() : "null" );
		buffer.append("'");
		return buffer.toString();
	}

	/**
	 * First try is to run this <code>run()</code> method.
	 */
	public void xrun() {
		System.out.println("SampleStarter.run()");
		System.out.println(this);
	}
	
	/**
	 * If you want to get the args-Array from the command line
	 * use the signature <code>run(String[] args)</code>. But then there must 
	 * not be a run() because that is executed prior to this.
	 * @param args The arguments as specified on the command line
	 */
	public void run(String[] args) {
		System.out.println("SampleStarter.run(String[])");
		System.out.println("- args.length: " + args.length);
		for (String arg : args) System.out.println("  - " + arg);
		System.out.println(this);
	}
	
}
