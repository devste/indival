package org.indival.model.stat.edge;

/*
 * Any edge in Indival should implement this interface.
 */
public interface IvEdge {
    
    /*
     * Return the edge type for given source and destination node type
     */
    public IvEdgeType getEdgeType();

}
