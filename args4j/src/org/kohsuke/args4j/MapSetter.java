package org.kohsuke.args4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    	String[] parts = String.valueOf(value).split("=");
    	String mapKey   = parts[0];
    	String mapValue = (parts.length > 1) ? parts[1] : null;

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
