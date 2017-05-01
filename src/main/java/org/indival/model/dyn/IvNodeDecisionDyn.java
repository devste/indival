package org.indival.model.dyn;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNodeDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IvNodeDecisionDyn extends IvNodeDecision implements IvNodeDynamic {

    private Logger log = LoggerFactory.getLogger(this.getClass().toString());

    private IvGraph graph;
    private SortedMap<String, IvNodeAlternativeDyn> alternatives = new TreeMap<>();
    private IvNodeAlternativeDyn selectedAlternative = null;

    public IvNodeDecisionDyn(String identifier) {
	super(identifier);
    }

    /*
     * Initialises the alternatives. Iterates over all the edges to find edges
     * where this object is the source and the destination is an
     * IvNodeAlternative.
     */
    private void initAlternatives() {
	Set<IvEdge> edges = graph.edgeSet();
	for (IvEdge e : edges) {
	    if (graph.getEdgeSource(e) == this && (graph.getEdgeTarget(e) instanceof IvNodeAlternativeDyn)) {
		this.alternatives.put(graph.getEdgeTarget(e).getIdentifier(),
			(IvNodeAlternativeDyn) graph.getEdgeTarget(e));
	    }
	}
    }

    public SortedMap<String, IvNodeAlternativeDyn> getAlternatives() {
	return this.alternatives;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.indival.model.dyn.IvNodeDynamic#update()
     * 
     * Do nothing here
     */
    @Override
    public void update() {
    }

    @Override
    public void init(IvGraph graph) {
	this.graph = graph;
	initAlternatives();
    }

    /*
     * Returns the nodeId of the currently selected node (or an empty string if
     * none is selected).
     */
    public String getSelectednodeId() {
	if (this.selectedAlternative != null) {
	    return this.selectedAlternative.getIdentifier();
	} else {
	    return "";
	}
    }

    /*
     * Sets the nodeId as selection. Ignores if the nodeId is not an alternative
     * of if the nodeId is already selected.
     */
    public void setSelectedAlternative(String nodeId) {
	if (!this.getSelectednodeId().equals(nodeId)) {
	    // Only do work if we need to change the state
	    if (this.alternatives.containsKey(nodeId)) {
		IvNodeAlternativeDyn node = this.alternatives.get(nodeId);
		if (this.selectedAlternative != null) {
		    this.selectedAlternative.setUnselected();
		}
		log.debug("Setting alternative to nodeId {} on node {}", nodeId, node.getIdentifier());
		node.setSelected();
		this.selectedAlternative = node;
		node.update();
	    }
	}
    }

}
