package org.indival.model.stat.edge;

import org.indival.model.stat.node.IvNodeType;

/*
 * This class implements a Matcher to return the IvEdgeType corresponding to the IvNodeType of the source and the destination node.
 */
public class IvEdgeTypeMatcher {

    /*
     * Empty private constructor to hide the implicit public constructor
     */
    private IvEdgeTypeMatcher() {

    }

    /*
     * Returns the matching IvEdgeType. Returns null if there is no type to
     * match. This is a rather stupid switch / case implementation.
     * 
     * TODO: improve implementation with some kind of HashMap or similar
     */
    public static IvEdgeType matcher(IvNodeType source, IvNodeType destination) {
	IvEdgeType type = null;
	switch (destination) {
	case DECISION:
	    type = getInformationalType(source);
	    break;
	case VALUE:
	    type = getFunctionalType(source);
	    break;
	case ALTERNATIVE:
	    type = getAlternativeType(source);
	    break;
	default:
	    type = null;
	    break;
	}
	return type;
    }

    /*
     * Lookup the IvEdgeType for a possible informational arc
     */
    private static IvEdgeType getInformationalType(IvNodeType source) {
	IvEdgeType type = null;
	switch (source) {
	case CHANCE:
	    type = IvEdgeType.INFORMATIONAL_CHANCE;
	    break;
	case DECISION:
	    type = IvEdgeType.INFORMATIONAL_DECISION;
	    break;
	default:
	    break;
	}
	return type;
    }

    /*
     * Lookup the IvEdgeType for a possible functional arc
     */
    private static IvEdgeType getFunctionalType(IvNodeType source) {
	IvEdgeType type = null;
	switch (source) {
	case DECISION:
	    type = IvEdgeType.FUNCTIONAL_DECISION;
	    break;
	case ALTERNATIVE:
	    type = IvEdgeType.FUNCTIONAL_ALTERNATIVE;
	    break;
	default:
	    break;
	}
	return type;
    }

    /*
     * Lookup the IvEdgeType for a possible alternative arc
     */
    private static IvEdgeType getAlternativeType(IvNodeType source) {
	IvEdgeType type = null;
	switch (source) {
	case DECISION:
	    type = IvEdgeType.ALTERNATIVE_DECISION;
	    break;
	default:
	    break;
	}
	return type;
    }

}
