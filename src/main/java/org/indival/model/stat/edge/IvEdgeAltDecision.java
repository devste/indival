package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

public class IvEdgeAltDecision extends IvEdgeBase {

    protected IvEdgeAltDecision(IvNode source, IvNode destination) {
	super(source, destination, IvEdgeType.ALTERNATIVE_DECISION);
    }

}
