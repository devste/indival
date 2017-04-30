package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

public class IvEdgeFuncDecision extends IvEdgeBase {

    protected IvEdgeFuncDecision(IvNode source, IvNode destination) {
	super(source, destination, IvEdgeType.FUNCTIONAL_DECISION);
    }

}
