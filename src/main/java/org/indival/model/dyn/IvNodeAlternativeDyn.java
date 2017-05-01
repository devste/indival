package org.indival.model.dyn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNodeAlternative;

public class IvNodeAlternativeDyn extends IvNodeAlternative implements IvNodeDynamic {
    boolean selected = false;
    private IvGraph graph;
    List<IvNodeValueDyn> valueNodes = new ArrayList<>();

    private void initValueRecipients() {
	Set<IvEdge> edges = graph.edgeSet();
	for (IvEdge e : edges) {
	    if (graph.getEdgeSource(e) == this && graph.getEdgeTarget(e) instanceof IvNodeValueDyn) {
		this.valueNodes.add((IvNodeValueDyn) graph.getEdgeTarget(e));
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
    }

    @Override
    public void update() {
	for (IvNodeValueDyn node : valueNodes) {
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

}
