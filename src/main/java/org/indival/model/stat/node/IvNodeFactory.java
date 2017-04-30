package org.indival.model.stat.node;

public class IvNodeFactory {
    /*
     * Hide the implicit public constructor
     */
    private IvNodeFactory() {
    }

    /*
     * Returns a new IvNode of the correct subclass (determined by type).
     */
    public static IvNode getNode(IvNodeType type, String identifier) {
	switch (type) {
	case ALTERNATIVE:
	    return new IvNodeAlternative(identifier);
	case CHANCE:
	    return new IvNodeChance(identifier);
	case DECISION:
	    return new IvNodeDecision(identifier);
	case VALUE:
	    return new IvNodeValue(identifier);
	default:
	    return new IvNodeBase(type, identifier);
	}
    }

}
