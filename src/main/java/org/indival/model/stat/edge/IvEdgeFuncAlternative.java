package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

public class IvEdgeFuncAlternative extends IvEdgeBase {

    protected IvEdgeFuncAlternative(IvNode source, IvNode destination) {
	super(source, destination, IvEdgeType.FUNCTIONAL_ALTERNATIVE);
    }

}
