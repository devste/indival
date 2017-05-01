package org.indival.model.dyn;

import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNodeValue;

public class IvNodeValueDyn extends IvNodeValue implements IvNodeDynamic {
    private IvGraph graph;

    public IvNodeValueDyn(String identifier) {
	super(identifier);
    }
    
    @Override
    public String toString(){
	return super.toString() + this.getUnit();
    }

    @Override
    public void init(IvGraph graph) {
	this.graph = graph;
    }

    @Override
    public void update() {
	// TODO Auto-generated method stub

    }

}
