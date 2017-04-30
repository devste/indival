package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNode;

/*
 * Factory method to create an edge of the correct subclass.
 */
public class IvEdgeFactory {
    /*
     * Hide the implicit public constructor.
     */
    private IvEdgeFactory() {

    }

    public static IvEdge getEdge(IvNode source, IvNode destination) {
	IvEdgeType type = IvEdgeTypeMatcher.matcher(source.getType(), destination.getType());
	if (type == null) {
	    return null;
	}
	switch (type) {
	case ALTERNATIVE_DECISION:
	    return new IvEdgeAltDecision(source, destination);
	case FUNCTIONAL_ALTERNATIVE:
	    return new IvEdgeFuncAlternative(source, destination);
	case FUNCTIONAL_DECISION:
	    return new IvEdgeFuncDecision(source, destination);
	case INFORMATIONAL_CHANCE:
	    return new IvEdgeInfoChance(source, destination);
	case INFORMATIONAL_DECISION:
	    return new IvEdgeInfoDecision(source, destination);
	default:
	    return new IvEdgeBase(source, destination, type);
	}

    }

}
