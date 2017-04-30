package org.indival.model.stat;

/*
 * Any node in Indival should implement this interface
 */
public interface IvNode {
    
    /*
     * Return the type of this Node
     */
    public IvNodeType getType();
    
    /*
     * Return a unique Identifier. Not named getID() to prevent conflict with jgraphx
     */
    public String getIdentifier();

}
