package org.kohsuke.args4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Starter class which uses reflection to instantiate the business
 * class, parse the command line parameters, sets the fields of the
 * business class and doing the help message handling.
 *  
 * @author Jan Materne
 */
public class Starter {
	
	/**
	 * The name of the JavaVM property which stores the class name of
	 * the business class.
	 * {@value} 
	 */
	public static final String PARAMETER_NAME = "mainclass";

	public static void main(String[] args) {
		String classname = System.getProperty(PARAMETER_NAME);
		CmdLineParser parser = null;
		boolean classHasArgument = false;
		boolean classHasOptions  = false;
		
		// Check the requirement: must specify the class to start
		if (classname == null || "".equals(classname)) {
			System.err.println("The system property '" 
					+ PARAMETER_NAME
					+ "' must contain the classname to start.");
			System.exit(-1);
		}
		
		try {
			Class clazz = Class.forName(classname);
			Object bean = clazz.newInstance();
			parser = new CmdLineParser(bean);
			
			// for help output
			classHasArgument = hasAnnotation(clazz, Argument.class);
			classHasOptions  = hasAnnotation(clazz, Option.class);
			
			parser.parseArgument(args);
			
			// try starting   run()
			Method m;
			boolean couldInvoke = false;
			try {
				m = clazz.getMethod("run", (Class[]) null);
				m.invoke(bean, (Object[]) null);
				couldInvoke = true;
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (NoSuchMethodException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}

			// try starting   run(String[])
			if (!couldInvoke) try {
				m = clazz.getMethod("run", String[].class);		
				m.invoke(bean, new Object[]{args});
				couldInvoke = true;
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		} catch (ClassNotFoundException e) {
			// wrong classpath setting
			System.err.println("Cant find the class '" 
					+ classname
					+ "' as specified in the system property '"
					+ PARAMETER_NAME + "'.");
		} catch (CmdLineException e) {
			// wrong argument enteres, so print the usage message as
			// supplied by args4j
			System.err.println(e.getMessage());
			System.err.print(classname);
			if (classHasOptions)  System.err.print(" [options]");
			if (classHasArgument) System.err.print(" arguments");
			System.err.println();
			if (parser != null)
				parser.printUsage(System.err);
		} catch (Exception e) {
			// Must be an unhandled business exception, so we can only
			// print stacktraces.
			e.printStackTrace();
		}
	}

	public static boolean hasAnnotation(Class clazz, Class<? extends Annotation> annotation) {
		if (clazz.getAnnotation(annotation)!=null) return true;
		for (Field f : clazz.getFields()) {
			if (f.getAnnotation(annotation)!=null) return true;
		}
		for (Method m : clazz.getMethods()) {
			if (m.getAnnotation(annotation)!=null) return true;
		}
		return false;
	}
	
}
