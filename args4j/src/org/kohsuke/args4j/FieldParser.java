package org.kohsuke.args4j;

import java.lang.reflect.Field;

import org.kohsuke.args4j.spi.ConfigElement;
import org.kohsuke.args4j.spi.OptionImpl;
import org.kohsuke.args4j.spi.Setters;

/**
 * This metadata parser makes all field available to the CmdLineParser.
 * @author Jan Materne
 *
 */
public class FieldParser {

	public void parse(CmdLineParser parser, Object bean) throws ClassNotFoundException {
        for(Class c=bean.getClass(); c!=null; c=c.getSuperclass()) {
        	System.out.println("Class: " + c);
        	for( Field f : c.getDeclaredFields() ) {
        		Option o = new OptionImpl(createConfigElement(f));
				parser.addOption(Setters.create(f, bean), o );
        	}
        }
	}

	private ConfigElement createConfigElement(Field f) {
		ConfigElement rv = new ConfigElement();
		rv.field = f.getName();
		rv.name = "-" + f.getName();
		return rv;
	}

}
