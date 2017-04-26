package org.indival.model;

import java.util.HashMap;

public class IndiNodeValue extends IndiNodeBase {
    /**
     * 
     */
    private static final long serialVersionUID = -1840101647258576654L;
    private HashMap<String, Float> sourceValueMap = new HashMap<>();
    private String unit = "";

    public IndiNodeValue(String nodeId) {
	super(nodeId, IndiNodeType.VALUE);
    }

    public void setValue(String nodeId, Float value) {
	this.sourceValueMap.put(nodeId, value);
    }

    /*
     * calculates the current value, according to which options have been chosen
     * in decision nodes.
     */
    public Float calculateCurrentValue() {
	Float result = Float.valueOf("0");
	// Iterate through each decision
	for (Float val : this.sourceValueMap.values()) {
	    Float selVal = val;
	    result += selVal;
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.example.idifosop.model.IndiNodeBase#toString()
     */
    @Override
    public String toString() {
	String name = this.getName();
	String currentValue = this.calculateCurrentValue().toString();
	String unit = this.unit;
	return name + "\n" + currentValue + "\n" + unit;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
	return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit) {
	this.unit = unit;
    }

}
