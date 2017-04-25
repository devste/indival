package org.indival.model;

import java.util.HashMap;
import java.util.Map;

public class IndiNodeDecision extends IndiNodeBase {
    /**
     * 
     */
    private static final long serialVersionUID = 7478985657138478254L;
    private HashMap<String, IndiNodeDecisionOption> decisionOptions = new HashMap<>();
    // implicit options are the options created when a decision node is linked
    // to another decision node.
    private HashMap<String, IndiNodeDecisionOption> implicitOptions = new HashMap<>();

    public IndiNodeDecision(String nodeId) {
	super(nodeId, IndiNodeType.DECISION);
    }

    /**
     * @return the decisionOptions
     */
    public Map<String, IndiNodeDecisionOption> getDecisionOptions() {
	HashMap<String, IndiNodeDecisionOption> ret = new HashMap<>();
	ret.putAll(decisionOptions);
	ret.putAll(implicitOptions);
	return ret;
    }

    /**
     * @param decisionOptions
     *            the decisionOptions to set
     */
    // public void setDecisionOptions(Hashtable<String, IndiNodeDecisionOption>
    // decisionOptions) {
    // this.decisionOptions = decisionOptions;
    // }

    /*
     * Return a single decision option
     */
    public IndiNodeDecisionOption getDecisionOption(String key) {
	return decisionOptions.get(key);
    }

    /*
     * Replaces an existing IndiNodeDecisionOption. Returns true if the object
     * was replaced. Returns false if the same key is already in the list. In
     * that case, the list is not changed and addDecisionOption should be used
     * instead.
     */
    public void putDecisionOption(String key, IndiNodeDecisionOption value) {
	this.decisionOptions.put(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.indival.model.IndiNodeBase#addDestinationNode(org.indival.model.
     * IndiNode)
     * 
     * We override addDestinationNode, because we need to set the implicit
     * decision option if a decision node is linked to another decision node.
     */
    @Override
    public boolean addDestinationNode(IndiNode in) {
	String nodeId = in.getNodeId();
	if (!this.destinationNodes.containsKey(nodeId)) {
	    this.destinationNodes.put(nodeId, in);
	    // if the other node is also a decision node
	    if (in.getType() == IndiNodeType.DECISION) {
		String indoNodeId = this.getNodeId() + "_" + nodeId;
		IndiNodeDecisionOption indo = new IndiNodeDecisionOption(indoNodeId, this);
		indo.setName(nodeId + " - " + in.getName());
		this.implicitOptions.put(nodeId, indo);
	    }
	    return true;
	} else {
	    return false;
	}
    }

}
