package org.kohsuke.args4j.spi;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Config;
import org.kohsuke.args4j.MetadataParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Config.ConfigElement;

/**
 * Parses an XML-file specifying the 'annotations'.
 * The XML must have the structure:
 * <pre>
 * &lt;args>
 *     &lt;option field="" method="" name="" usage="" metavar="" handler=""/>
 *     &lt;argument field="" method="" usage="" metavar="" handler=""/>
 * &lt;/args>
 * </pre>
 * Exactly one of the attributes 'field' or 'method' must be set.
 * The 'handler' value specifies a full qualified class name.
 *
 * <h3>Example</h3>
 * <pre>
 * &lt;args>
 *     &lt;option field="recursive" name="-r" usage="recursively run something"/>
 *     &lt;option field="out" name="-o" usage="output to this file" metavar="OUTPUT"/>
 *     &lt;option method="setStr(String)" name="-str"/>
 *     &lt;option field="data" name="-custom" handler="org.kohsuke.args4j.spi.BooleanOptionHandler" usage="boolean value for checking the custom handler"/>
 *     &lt;argument field="arguments"/>
 * &lt;args>
 * </pre>
 *
 * @author Jan Matèrne
 */
public class XmlParser extends MetadataParser {

	/**
	 * The input stream from where to read the XML.
	 */
	private InputStream configInputStream;

	/**
	 * Stores the input resource stream which is derived from the bean class
	 * and returns <tt>true</tt> if the resource exists.
	 * The resource has the same name as the class with a suffix <tt>.args</tt> and
	 * is in the same directory as the class (with package).
	 * @see org.kohsuke.args4j.MetadataParser#canWorkFor(java.lang.Object)
	 */
	@Override
	public boolean canWorkFor(Object bean) {
		configInputStream = bean.getClass().getResourceAsStream(bean.getClass().getSimpleName() + ".args");
		return configInputStream != null;
	}

	@Override
	public void parseInternal(Object bean) {
		try {
			Config config = Config.parse(configInputStream);
			AccessibleObject methodOrField;
			for(Config.ConfigElement ce : config.options) {
				Option option = new OptionImpl(ce);
				methodOrField = findMethodOrField(bean, ce.field, ce.method);
				addArgument(methodOrField, option);
			}
			for (Config.ConfigElement ce : config.arguments) {
				Argument argument = new ArgumentImpl(ce);
				methodOrField = findMethodOrField(bean, ce.field, ce.method);
				addArgument(methodOrField, argument);
			}
		} catch (Exception e) {
			throw new RuntimeException("Problems while reading the args-confguration.", e);
		}
	}


	/**
	 * Finds a {@link java.lang.reflect.Method} or {@link java.lang.reflect.Method} in the bean
	 * instance with the requested name.
	 * @param bean    bean instance
	 * @param field   name of the field (field XOR method must be specified)
	 * @param method  name of the method (field XOR method must be specified)
	 * @return the reflection reference
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	private AccessibleObject findMethodOrField(Object bean, String field, String method) throws SecurityException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
		AccessibleObject rv = null;
		if (field != null) {
			rv = bean.getClass().getDeclaredField(field);
		} else {
			String methodName = method.substring(0, method.indexOf("("));
			String[] params = method.substring(method.indexOf("(")+1, method.indexOf(")")).split(",");
			Class[] paramTypes = new Class[params.length];
			for(int i=0; i<params.length; i++) {
				String className = params[i];
				if (className.indexOf('.') < 0) {
					className = "java.lang." + className;
				}
				paramTypes[i] = Class.forName(className);
			}
			rv = bean.getClass().getMethod(methodName, paramTypes);
		}
		return rv;
	}

	/**
	 * Implementation of @Option so we can instantiate it.
	 * @author Jan Matèrne
	 */
	class OptionImpl extends AnnotationImpl implements Option {
		protected OptionImpl(ConfigElement ce) throws ClassNotFoundException {
			super(ce);
			name = ce.name;
			annotationType = Option.class;
		}
		public String name;
		public String name() {
			return name;
		}
	}

	/**
	 * Implementation of @Argument so we can instantiate it.
	 * @author Jan Matèrne
	 */
	class ArgumentImpl extends AnnotationImpl implements Argument {
		protected ArgumentImpl(ConfigElement ce) throws ClassNotFoundException {
			super(ce);
			annotationType = Argument.class;
		}
	}

	/**
	 * Base class for the @Option and @Argument implementation classes.
	 * @author Jan Matèrne
	 */
	class AnnotationImpl {
		protected AnnotationImpl(ConfigElement ce) throws ClassNotFoundException {
			aliases = ce.aliases != null ? ce.aliases : new String[]{};
			if (ce.handler != null) {
				handler = (Class<? extends OptionHandler>) Class.forName(ce.handler);
			} else {
				handler = OptionHandler.class;
			}
			metaVar = ce.metavar != null ? ce.metavar : "";
			multiValued = ce.multiValued;
			required = ce.required;
			usage = ce.usage != null ? ce.usage : "";
		}
		public String[] aliases;
		public String[] aliases() {
			return aliases;
		}
		public Class<? extends OptionHandler> handler;
		public Class<? extends OptionHandler> handler() {
			return handler;
		}
		public String metaVar;
		public String metaVar() {
			return metaVar;
		}
		public boolean multiValued;
		public boolean multiValued() {
			return multiValued;
		}
		public boolean required;
		public boolean required() {
			return required;
		}
		public String usage;
		public String usage() {
			return usage;
		}
		public Class<? extends Annotation> annotationType;
		public Class<? extends Annotation> annotationType() {
			return annotationType;
		}
		public int index;
		public int index() {
			return index;
		}
	}

}
