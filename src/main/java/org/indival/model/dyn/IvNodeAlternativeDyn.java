package org.indival.model.dyn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNodeAlternative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IvNodeAlternativeDyn extends IvNodeAlternative implements IvNodeDynamic {
    boolean selected = false;
    private IvGraph graph;
    List<IvNodeValueDyn> valueNodes = new ArrayList<>();
    IvNodeDecisionDyn ancestor;

    private Logger log = LoggerFactory.getLogger(this.getClass().toString());

    private void initValueRecipients() {
	Set<IvEdge> edges = graph.edgeSet();
	for (IvEdge e : edges) {
	    if (graph.getEdgeSource(e) == this && graph.getEdgeTarget(e) instanceof IvNodeValueDyn) {
		this.valueNodes.add((IvNodeValueDyn) graph.getEdgeTarget(e));
	    }
	}
	log.debug("{}: valueNodes has been initialized with {} entries", this.getIdentifier(), this.valueNodes.size());
    }

    private void initAncestor() {
	Set<IvEdge> edges = graph.edgeSet();
	for (IvEdge e : edges) {
	    if (graph.getEdgeTarget(e) == this && graph.getEdgeSource(e) instanceof IvNodeDecisionDyn) {
		IvNodeDecisionDyn ancestor = (IvNodeDecisionDyn) graph.getEdgeSource(e);
		log.debug("{}: Setting ancestor to {}", this.getIdentifier(), ancestor);
		this.ancestor = ancestor;
		break;
	    }
	}
    }

    public IvNodeAlternativeDyn(String identifier) {
	super(identifier);
    }

    @Override
    public void init(IvGraph graph) {
	this.graph = graph;
	initValueRecipients();
	initAncestor();
    }

    @Override
    public void update() {
	for (IvNodeValueDyn node : valueNodes) {
	    log.debug("{}: sending update to {}", this.getIdentifier(), node.getIdentifier());
	    node.update();
	}
    }

    public void setSelected() {
	this.selected = true;
    }

    public void setUnselected() {
	this.selected = false;
    }

    public boolean isSelected() {
	return this.selected;
    }

    /*
     * If the alternative is selected, then it will return the value, otherwise
     * it will return 0;
     */
    public Float getSelectedValue(String nodeId) {
	Float f = Float.valueOf("0");
	if (this.selected) {
	    f = this.getConfiguredValue(nodeId);
	}
	return f;
    }

    public IvNodeDecisionDyn getAncestor() {
	return this.ancestor;
    }

}
