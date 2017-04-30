package org.indival.model.stat.jgrapht;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.node.IvNode;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

/*
 * Provides an Indival graph.
 */
public class IvGraph extends DirectedAcyclicGraph<IvNode, IvEdge> {

    /**
     * 
     */
    private static final long serialVersionUID = 8073191574473080079L;

    public IvGraph(){
	super(IvEdge.class);
    }

}
