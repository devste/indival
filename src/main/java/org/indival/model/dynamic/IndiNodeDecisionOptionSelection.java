package org.indival.model.dynamic;

import org.indival.model.DecisionOptionSelection;
import org.indival.model.IndiNodeDecision;
import org.indival.model.IndiNodeDecisionOption;

public class IndiNodeDecisionOptionSelection implements DecisionOptionSelection {
	private String selectedOption = "";
	private IndiNodeDecision ind;

	public IndiNodeDecision getIndiNodeDecision() {
		return this.ind;
	}

	public IndiNodeDecisionOptionSelection(IndiNodeDecision node) {
		this.ind = node;
	}

	@Override
	public IndiNodeDecisionOption getSelectedOption() {
		return this.ind.getDecisionOptions().get(selectedOption);
	}

	@Override
	public boolean setSelectedOption(String key) {
		if (this.ind.getDecisionOptions().containsKey(key)) {
			this.selectedOption = key;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.indival.model.DecisionOptionSelection#unsetSelectedOption()
	 * 
	 * Unsets any selection previously made.
	 */
	@Override
	public void unsetSelectedOption() {
		this.selectedOption = "";

	}

	/*
	 * Returns the value of the currently selected option. Returns the value "0"
	 * if nothing is selected OR: if the value for the selected option hasn't
	 * been set.
	 */
	@Override
	public Float selectedOptionValue(String valueNodeId) {
		Float ret = Float.valueOf("0");
		IndiNodeDecisionOption indo = getSelectedOption();
		if (indo != null) {
			ret += indo.getValue(valueNodeId);
		}
		return ret;
	}

}
