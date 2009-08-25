package org.kohsuke.args4j;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

public abstract class MetadataParser {
	private final List<Pair> annotations = new ArrayList<Pair>();

	public abstract void parse(Object bean);

	public void addArgument(AccessibleObject methodOrField, Object argumentOrOption) {
		annotations.add(new Pair(methodOrField, argumentOrOption));
	}

	public List<Pair> getAnnotations() {
		return annotations;
	}

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
