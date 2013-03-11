/**
 * Copyright (c) 2008-2009, jhm
 * Copyright (c) 2012, Martin Schroeder, Intel Mobile Communications GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */


package org.kohsuke.args4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kohsuke.args4j.spi.Setter;

/**
 * This setter adds a new entry to a Map.
 * The value which is passed is split into the Map-key and Map-value by a "=".
 * <p>
 * <tt>-map key=value</tt></p>
 */
public class MapSetter implements Setter {
	private final Field f;
	private final Object bean;

	private static final Pattern keyValP = Pattern.compile("([^=]*)=(.*)");

	public MapSetter(Object bean, Field f) {
		super();
		this.f = f;
		this.bean = bean;
	}

	public Class getType() {
		return f.getType();
	}

	public boolean isMultiValued() {
		return false;
	}

	public void addValue(Object value) {
		if (String.valueOf(value).indexOf('=') == -1) {
			throw new RuntimeException(Messages.FORMAT_ERROR_FOR_MAP.format());
		}
		
		String mapKey = null;
		String mapValue = null;
		
		//Splitting off the key from the value
		Matcher m = keyValP.matcher(String.valueOf(value));
		if (m.find()) {
			mapKey = m.group(1);
			mapValue = m.group(2);
			if (mapValue.isEmpty()) {
				mapValue = null;
			}
		} else {
			//Use old implementation as fall-back
			String[] parts = String.valueOf(value).split("=");
			mapKey   = parts[0];
			mapValue = (parts.length > 1) ? parts[1] : null;
		}
		
		if (mapKey == null || mapKey.length()==0) {
			throw new RuntimeException(Messages.MAP_HAS_NO_KEY.format());
		}

		try {
			addValue(mapKey, mapValue);
		} catch (IllegalAccessException _) {
			// try again
			f.setAccessible(true);
			try {
				addValue(mapKey, mapValue);
			} catch (IllegalAccessException e) {
				throw new IllegalAccessError(e.getMessage());
			}
		}
	}

	private void addValue(Object key, Object value) throws IllegalArgumentException, IllegalAccessException {
		Map map = (Map) f.get(bean);
		if (map == null) {
			// Field is null so set it to an empty Map
			map = new HashMap();
			// and reset the field on the bean not just the local reference
			f.set(bean,	map);
		}
		map.put(key, value);
	}

}
