package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

public class IvEdgeInfoChance extends IvEdgeBase {

    public IvEdgeInfoChance(IvNode source, IvNode destination) {
	super(source, destination, IvEdgeType.INFORMATIONAL_CHANCE);
    }

}
