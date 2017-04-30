package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

public class IvEdgeInfoDecision extends IvEdgeBase {

    public IvEdgeInfoDecision(IvNode source, IvNode destination) {
	super(source, destination, IvEdgeType.INFORMATIONAL_DECISION);
    }

}
