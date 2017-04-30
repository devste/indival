package org.indival.model.stat.jgrapht;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.edge.IvEdgeAltDecision;
import org.indival.model.stat.edge.IvEdgeFactory;
import org.indival.model.stat.edge.IvEdgeType;
import org.indival.model.stat.node.IvNode;
import org.indival.model.stat.node.IvNodeAlternative;
import org.indival.model.stat.node.IvNodeDecision;
import org.indival.model.stat.node.IvNodeValue;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

/*
 * Provides an Indival graph.
 */
public class IvGraph extends DirectedAcyclicGraph<IvNode, IvEdge> {

    /*
     * This is to circumvent a ConcurrentModificationException. We store all the
     * edges to add into a list and add them at the very end.
     */
    private class edgeAdder {
	public edgeAdder(IvNode source, IvNode destination, IvEdge edge) {
	    this.source = source;
	    this.destination = destination;
	    this.edge = edge;
	}

	public IvNode source;
	public IvNode destination;
	public IvEdge edge;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8073191574473080079L;
    private Logger log = LoggerFactory.getLogger(this.getClass().toString());

    public IvGraph() {
	super(IvEdge.class);
    }

    /*
     * This allows postProcessing of the graph parsed from a file. This is
     * required, as after parsing from a file the alternative nodes are not
     * connected to the value nodes.
     * 
     * TODO: document what we're doing here much better
     */
    public void postProcessFunctionalAlternative() {
	List<edgeAdder> edgeadder = new ArrayList<>();
	Set<IvEdge> edgeSet = this.edgeSet();
	for (IvEdge edge : edgeSet) {
	    // we're looking for all decision -> functional edges.
	    if (edge.getClass() == IvEdgeAltDecision.class) {
		log.debug("postProcessFunctionalAlternative: found edge {}", edge.toString());
		IvNodeDecision source = (IvNodeDecision) this.getEdgeSource(edge);
		IvNodeAlternative destination = (IvNodeAlternative) this.getEdgeTarget(edge);
		Set<String> valNodeIds = destination.getConfiguredValueNodeIds().keySet();
		Set<IvNode> decisionAncestors = this.getDescendants(this, source);
		log.debug(
			"postProcessFunctionalAlternative: sourceId: {}, destId: {}, numValNodeIds: {}, numDecisionAncestors: {}",
			source.getIdentifier(), destination.getIdentifier(), valNodeIds.size(),
			decisionAncestors.size());
		for (IvNode node : decisionAncestors) {
		    if (node.getClass() == IvNodeValue.class && valNodeIds.contains(node.getIdentifier())) {
			IvEdge newEdge = IvEdgeFactory.getEdge(destination, node);
			// store the new edge to add after looping
			// (ConcurrentModificationException).
			edgeadder.add(new edgeAdder(destination, node, newEdge));
		    }
		}
	    }
	}
	// finally we can add all the new edges
	for (edgeAdder ea : edgeadder) {
	    boolean ret = this.addEdge(ea.source, ea.destination, ea.edge);
	    log.debug("postProcessFunctionalAlternative: adding edge: {}, successful: {}", ea.toString(), ret);
	}
    }

    public void toMxGraph(mxGraph mxg, mxGraphModel mxgModel) {
	Object parent = mxg.getDefaultParent();
	Set<IvEdge> edgeSet = this.edgeSet();
	int displayOffset = 0;
	for (IvEdge e : edgeSet) {
	    IvEdgeType etype = e.getEdgeType();
	    // if it's a type that is in an influence diagramme
	    if (etype == IvEdgeType.FUNCTIONAL_DECISION || etype == IvEdgeType.INFORMATIONAL_CHANCE
		    || etype == IvEdgeType.INFORMATIONAL_DECISION) {
		IvNode source = this.getEdgeSource(e);
		IvNode dest = this.getEdgeTarget(e);
		Object sourceCell = mxgModel.getCell(source.getIdentifier());
		Object destCell = mxgModel.getCell(dest.getIdentifier());
		if (sourceCell == null) {
		    mxg.insertVertex(parent, source.getIdentifier(), source, 0 + 100 * displayOffset,
			    0 + 20 * displayOffset, 100, 50, source.getType().name());
		}
		if (destCell == null) {
		    mxg.insertVertex(parent, dest.getIdentifier(), dest, 0 + 100 * (displayOffset + 1),
			    0 + 20 * (displayOffset + 1), 100, 50, dest.getType().name());
		}
		sourceCell = mxgModel.getCell(source.getIdentifier());
		destCell = mxgModel.getCell(dest.getIdentifier());
		mxg.insertEdge(parent, e.toString(), e, sourceCell, destCell);
		displayOffset += 2;
	    }
	}
    }
}
