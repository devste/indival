package org.indival.model.stat.node;

import java.util.HashMap;

public class IvNodeBase implements IvNode {

    private IvNodeType type;
    private String identifier;
    protected HashMap<String, String> attributes = new HashMap<>();

    /*
     * Create a new IndiNode with only a type. This is the minimum requirement.
     */
    public IvNodeBase(IvNodeType type) {
	this.type = type;
    }

    public IvNodeBase(IvNodeType type, String identifier) {
	this(type);
	this.identifier = identifier;
    }

    @Override
    public IvNodeType getType() {
	return this.type;
    }

    @Override
    public String getIdentifier() {
	return this.identifier;
    }

    @Override
    public String toString() {
	return this.identifier;
    }

    @Override
    public void setAttribute(String key, String value) {
	this.attributes.put(key, value);
    }

    @Override
    public String getAttribute(String key) {
	if (this.attributes.containsKey(key)) {
	    return this.attributes.get(key);
	} else {
	    return null;
	}
    }

    @Override
    public String getName() {
	String name = getAttribute("name");
	return (name != null) ? name : "";
    }

}
