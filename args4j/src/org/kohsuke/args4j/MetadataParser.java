package org.kohsuke.args4j;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for reading the metadata for the mapping from command line options to
 * class fields. One implementation is reading the metadata from the Args4J annotations
 * in the business class.
 * This is the step before parsing the string array from the command line.
 *
 * @author Jan Matèrne
 */
public abstract class MetadataParser {
	/** Found 'annotations'. */
	private final List<Pair> annotations = new ArrayList<Pair>();

	/**
	 *  The real parsing.
	 *  Just use {@link #addArgument(AccessibleObject, Object)} for storing the values.
	 *  The {@link CmdLineParser} itself uses these information for setting the real values.
	 *  @param bean the bean instance to set the values for.
	 */
	public abstract void parseInternal(Object bean);

	/**
	 * Parse the metadata.
	 * @param bean the bean instance
	 */
	public final void parse(Object bean) {
		annotations.clear();
		parseInternal(bean);
	}

	/**
	 * Checks if this parser can work or if something is missing in the environment
	 * (e.g. configuration files).
	 * @param bean the bean instance
	 * @return <tt>true</tt> if this parser is able to work
	 */
	public boolean canWorkFor(Object bean) {
		return true;
	}

	/**
	 * Stores an 'annotation'.
	 * @param methodOrField java.lang.reflect.Field or Method representing the target in the bean
	 * @param argumentOrOption Args4J Annotation @Argument or @Option
	 */
	public void addArgument(AccessibleObject methodOrField, Object argumentOrOption) {
		annotations.add(new Pair(methodOrField, argumentOrOption));
	}

	public List<Pair> getAnnotations() {
		return annotations;
	}

	/**
	 * Pair of Method|Field and Args4J-Annotation.
	 * @author Jan Matèrne
	 */
	public class Pair {
		private final AccessibleObject methodOrField;
		private final Object argumentOrOption;
		public Pair(AccessibleObject methodOrField, Object argumentOrOption) {
			this.argumentOrOption = argumentOrOption;
			this.methodOrField = methodOrField;
		}
		public AccessibleObject getMethodOrField() {
			return methodOrField;
		}
		public Object getArgumentOrOption() {
			return argumentOrOption;
		}
	}
}
