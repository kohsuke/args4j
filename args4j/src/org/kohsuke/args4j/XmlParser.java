package org.kohsuke.args4j;

import java.lang.reflect.AccessibleObject;
import java.net.URL;

import org.kohsuke.args4j.spi.ArgumentImpl;
import org.kohsuke.args4j.spi.ConfigElement;
import org.kohsuke.args4j.spi.OptionImpl;
import org.kohsuke.args4j.spi.Setters;
import org.xml.sax.InputSource;

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
 * @author Jan Materne
 */
public class XmlParser {
    public void parse(URL xml, CmdLineParser parser, Object bean) {
        parse(new InputSource(xml.toExternalForm()),parser,bean);
    }

	public void parse(InputSource xml, CmdLineParser parser, Object bean) {
		try {
			Config config = Config.parse(xml);
			for(ConfigElement ce : config.options) {
				Option option = new OptionImpl(ce);
                parser.addOption(Setters.create(parser, findMethodOrField(bean, ce.field, ce.method),bean), option);
			}
			for (ConfigElement ce : config.arguments) {
				Argument argument = new ArgumentImpl(ce);
				parser.addArgument(Setters.create(parser, findMethodOrField(bean, ce.field, ce.method),bean), argument);
			}
		} catch (Exception e) {
			throw new RuntimeException(Messages.METADATA_ERROR.format(), e);
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
		AccessibleObject rv;
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

}
