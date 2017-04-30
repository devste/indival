package org.indival.model.stat.node;

import java.util.HashMap;
import java.util.Map;

public class IvNodeAlternative extends IvNodeBase {
    /*
     * Contains the map of nodeId -> value for valueIds. It does not necessarily
     * represent valid connections to those nodes. This should be initialised
     * the first time someone tries to access a value.
     */
    Map<String, Float> valueMap = null;

    public IvNodeAlternative(String identifier) {
	super(IvNodeType.ALTERNATIVE, identifier);
    }
    
    /*
     * This method initializes the valueMap from the attributes.
     */
    private void initializeValueMap(){
	this.valueMap = new HashMap<>();
	for (String s : this.attributes.keySet()) {
	    if (s.startsWith("<") && s.endsWith(">")) {
		String key = s.substring(1, s.length() - 1);
		String rawValue = this.attributes.get(s);
		Float value = Float.valueOf(rawValue);
		this.valueMap.put(key, value);
	    }
	}
    }

    /*
     * Gets the value nodeIds that are configures on this alternative. The
     * nodeIds are not necessarily valid.
     */
    public Map<String, Float> getConfiguredValueNodeIds() {
	if(this.valueMap == null){
	    initializeValueMap();
	}
	return this.valueMap;
    }

}
