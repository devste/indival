package org.indival.model.dyn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNodeValue;

public class IvNodeValueDyn extends IvNodeValue implements IvNodeDynamic {
    private Float currentVal;
    private IvGraph graph;
    List<IvNodeAlternativeDyn> predecesorsAlternative = new ArrayList<>();

    private void initAlternatives() {
	Set<IvEdge> edges = graph.edgeSet();
	for (IvEdge e : edges) {
	    if (graph.getEdgeTarget(e) == this && graph.getEdgeSource(e) instanceof IvNodeAlternativeDyn) {
		this.predecesorsAlternative.add((IvNodeAlternativeDyn) graph.getEdgeSource(e));
	    }
	}
    }

    public IvNodeValueDyn(String identifier) {
	super(identifier);
    }

    @Override
    public String toString() {
	return super.toString() + "\n" + currentVal + "\n" + this.getUnit();
    }

    @Override
    public void init(IvGraph graph) {
	this.graph = graph;
	initAlternatives();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.indival.model.dyn.IvNodeDynamic#update()
     * 
     * This will update the current value from the alternative nodes;
     */
    @Override
    public void update() {
	Float f = Float.valueOf("0");
	for (IvNodeAlternativeDyn node : this.predecesorsAlternative) {
	    f += node.getSelectedValue(this.getIdentifier());
	}
	this.currentVal = f;
    }

}
