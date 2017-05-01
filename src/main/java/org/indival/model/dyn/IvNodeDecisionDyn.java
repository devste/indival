package org.indival.model.dyn;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNodeAlternative;
import org.indival.model.stat.node.IvNodeDecision;

public class IvNodeDecisionDyn extends IvNodeDecision implements IvNodeDynamic {

    private IvGraph graph;
    private SortedMap<String, IvNodeAlternative> alternatives = new TreeMap<>();

    public IvNodeDecisionDyn(String identifier) {
	super(identifier);
    }

    /*
     * Initialises the alternatives. Iterates over all the edges to find edges
     * where this object is the source and the destination is an IvNodeAlternative.
     */
    private void initAlternatives() {
	Set<IvEdge> edges = graph.edgeSet();
	for (IvEdge e : edges) {
	    if (graph.getEdgeSource(e) == this && (graph.getEdgeTarget(e) instanceof IvNodeAlternative)) {
		alternatives.put(graph.getEdgeTarget(e).getIdentifier(), (IvNodeAlternative) graph.getEdgeTarget(e));
	    }
	}
    }

    public SortedMap<String, IvNodeAlternative> getAlternatives() {
	return this.alternatives;
    }

    @Override
    public void update() {
	initAlternatives();
    }

    @Override
    public void init(IvGraph graph) {
	this.graph = graph;
	initAlternatives();
    }

}
