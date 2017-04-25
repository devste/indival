package org.indival.model;

import java.util.HashMap;
import java.util.Map;

public class IndiNodeDecision extends IndiNodeBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7478985657138478254L;
	private HashMap<String, IndiNodeDecisionOption> decisionOptions = new HashMap<>();

	public IndiNodeDecision(String nodeId) {
		super(nodeId, IndiNodeType.DECISION);
	}

	/**
	 * @return the decisionOptions
	 */
	public Map<String, IndiNodeDecisionOption> getDecisionOptions() {
		return decisionOptions;
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

}
