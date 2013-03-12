package org.kohsuke.args4j.spi;

import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.*;

public class MapOptionHandler extends OptionHandler<Map<?,?>> {

	public MapOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Map<?,?>> setter) {
		super(parser, option, setter);
        if (!(setter instanceof FieldSetter))
            throw new IllegalArgumentException("MapOptionHandler can only work with fields");
    }

	@Override
	public String getDefaultMetaVariable() {
		return null;
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
        FieldSetter fs = (FieldSetter)setter;
        Map v = (Map)fs.get();
        if (v==null) {
            v = createNewCollection(fs.getType());
            fs.addValue(v);
        }

        addToMap(params.getParameter(0),v);

        return 1;
	}

    /**
     * Creates a new instance of the collection.
     */
    protected Map createNewCollection(Class<? extends Map> type) {
        return new HashMap();
    }

    /**
     * Encapsulates how a single string argument gets converted into key and value.
     */
    protected void addToMap(String argument, Map m) throws CmdLineException {
    	if (String.valueOf(argument).indexOf('=') == -1) {
    		throw new CmdLineException(owner,Messages.FORMAT_ERROR_FOR_MAP.format());
    	}

		String mapKey;
		String mapValue;

		//Splitting off the key from the value
        int idx = argument.indexOf('=');
        if (idx>=0) {
            mapKey = argument.substring(0, idx);
            mapValue = argument.substring(idx + 1);
            if (mapValue.length()==0)
                // Kohsuke: I think this is a bad choice, but this is needed to remain backward compatible
                mapValue = null;
        } else {
            mapKey = argument;
            mapValue = null;
        }

    	if (mapKey.length()==0) {
    		throw new CmdLineException(owner,Messages.MAP_HAS_NO_KEY.format());
    	}

        addToMap(m, mapKey, mapValue);
    }

    /**
     * This is the opportunity to convert values to some typed objects.
     */
    protected void addToMap(Map m, String key, String value) {
        m.put(key,value);
    }

}
