package org.indival.model.stat.node;

public class IvNodeValue extends IvNodeBase {

    public IvNodeValue(String identifier) {
	super(IvNodeType.VALUE, identifier);
    }

    /*
     * Returns the unit set by the user
     */
    public String getUnit() {
	return this.getAttribute("unit");
    }

}
