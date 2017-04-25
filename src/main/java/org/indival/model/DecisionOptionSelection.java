package org.indival.model;

/*
 * This interface allows to control which decision options have
 * currently been selected. It also allows to block selections.
 */
public interface DecisionOptionSelection {

	/*
	 * Return the currently selected option.
	 */
	public IndiNodeDecisionOption getSelectedOption();

	/**
	 * @param key
	 *            the id of the option to set as currently selected option
	 * @return true, if the option could be selected. false if the option
	 *         couldn't be selected
	 */
	public boolean setSelectedOption(String key);

	/*
	 * removes any selection done
	 */
	public void unsetSelectedOption();

	/*
	 * Returns the value of the currently selected option.
	 */
	public Float selectedOptionValue(String valueNodeId);

}
