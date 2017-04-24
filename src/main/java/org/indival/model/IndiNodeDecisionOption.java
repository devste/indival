package org.indival.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IndiNodeDecisionOption extends IndiNodeBase {
    private HashMap<String, Float> destinationValueMap = new HashMap<>();
    private IndiNodeDecision parent;

    /**
     * 
     */
    private static final long serialVersionUID = -57317016122466197L;

    public IndiNodeDecisionOption(String nodeId, IndiNodeDecision parent) {
	super(nodeId, IndiNodeType.DECISIONOPTION);
	this.parent = parent;
    }

    /**
     * @return the parent
     */
    public IndiNodeDecision getParent() {
	return parent;
    }

    /*
     * Sets the value to a destination node.
     * 
     * The method checks if the nodeId of the node is actually a destination for
     * the parent IndiNodeDecision. If it is a destination, it will set the
     * value without any further checks. If it isn't a destination, it will
     * return false.
     */
    public boolean setValue(String nodeId, Float value) {
	if (this.getParent().isDestinationNode(nodeId)) {
	    this.destinationValueMap.put(nodeId, value);
	    return true;
	} else {
	    return false;
	}
    }

    public Float getValue(String nodeId) {
	Float ret = Float.valueOf("0");
	if (this.destinationValueMap.containsKey(nodeId)) {
	    ret += this.destinationValueMap.get(nodeId);
	}
	return ret;
    }

    public Map<String, Float> getDestinationValueMap() {
	return this.destinationValueMap;
    }

    @Override
    public String toString() {
	StringBuilder ret = new StringBuilder();
	ret.append("+ Decision: " + parent.getName() + ", Option: " + this.getName() + ", Valuemap: ");
	for (Entry<String, Float> entry : this.destinationValueMap.entrySet()) {
	    ret.append(", " + entry.getKey() + ":" + entry.getValue().toString() + " ");
	}
	return ret.toString();
    }

}
