package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

/*
 * The basic implementation for IvEdgeType
 */
public class IvEdgeBase implements IvEdge {
    
    private IvNode source, destination;
    private IvEdgeType type;
    
    protected IvEdgeBase(IvNode source, IvNode destination, IvEdgeType type){
	this.type = type;
	this.source = source;
	this.destination = destination;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.indival.model.stat.IvEdge#getEdgeType(org.indival.model.stat.
     * IvNodeType, org.indival.model.stat.IvNodeType)
     * 
     * This method implements the IvEdgeType lookup for all IvNodeType
     * combinations.
     * 
     * @returns null, if there is no valid edge type in Indival.
     */
    @Override
    public IvEdgeType getEdgeType() {
	return this.type;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     * 
     * Return the identifier
     */
    @Override
    public String toString(){
	return "";
    }



}
