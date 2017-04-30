package org.indival.model.stat;

public class IvNodeBase implements IvNode {

    private IvNodeType type;
    private String identifier;

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
    public String toString(){
	return this.identifier;
    }

}
