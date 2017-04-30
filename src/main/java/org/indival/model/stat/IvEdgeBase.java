package org.indival.model.stat;

/*
 * The basic implementation for IvEdgeType
 */
public class IvEdgeBase implements IvEdge {
    
    private IvNode source, destination;
    
    public IvEdgeBase(IvNode source, IvNode destination){
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
	return IvEdgeTypeMatcher.matcher(source.getType(), destination.getType());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     * 
     * Return the identifier
     */
    @Override
    public String toString(){
	return this.source.toString() + " -> " + this.destination.toString();
    }



}
