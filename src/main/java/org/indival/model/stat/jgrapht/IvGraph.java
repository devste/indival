package org.indival.model.stat.jgrapht;

import org.indival.model.stat.IvEdge;
import org.indival.model.stat.IvNode;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

/*
 * Provides an Indival graph.
 */
public class IvGraph extends DirectedAcyclicGraph<IvNode, IvEdge> {

    public IvGraph(){
	super(IvEdge.class);
    }

}
