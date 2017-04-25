package org.indival.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.indival.MainWindow;
import org.indival.model.IndiNode;
import org.indival.model.IndiNodeDecisionOption;
import org.indival.model.IndiNodeType;
import org.indival.model.IndiNodeValue;
import org.indival.model.dynamic.IndiNodeDecisionOptionSelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecisionOptionControl extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8105169920257321664L;
	private HashMap<String, IndiNodeDecisionOptionSelection> indList = new HashMap<>();
	private MainWindow mainWindow;
	private Logger log = LoggerFactory.getLogger(this.getClass().toString());

	public DecisionOptionControl(List<IndiNodeDecisionOptionSelection> indList, MainWindow mainWindow) {
		super(new GridLayout(0, 1));
		this.mainWindow = mainWindow;
		for (IndiNodeDecisionOptionSelection indos : indList) {
			this.indList.put(indos.getIndiNodeDecision().getNodeId(), indos);
		}

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		this.add(radioPanel);

		for (IndiNodeDecisionOptionSelection ind : this.indList.values()) {
			JPanel jp = radioButtonPanel(ind);
			this.add(jp);
		}

	}

	private JPanel radioButtonPanel(IndiNodeDecisionOptionSelection indos) {
		JPanel radioButtonPanel = new JPanel(new GridLayout(0, 1));
		// First a label for the whole Decision
		JLabel label = new JLabel(indos.getIndiNodeDecision().getName());
		radioButtonPanel.add(label);
		// Then an option per DecisionOption
		ButtonGroup group = new ButtonGroup();
		Collection<IndiNodeDecisionOption> indoList = indos.getIndiNodeDecision().getDecisionOptions().values();
		for (IndiNodeDecisionOption indo : indoList) {
			JRadioButton theButton = new JRadioButton(indo.getName());
			theButton.setActionCommand(indo.getNodeId());
			group.add(theButton);
			theButton.addActionListener(this);
			radioButtonPanel.add(theButton);
		}
		return radioButtonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		log.info("Option switching command: " + ac);

		// Set the selection
		String[] strings = ac.split("_");
		String decisionId = strings[0];
		String optionId = strings[1];
		IndiNodeDecisionOptionSelection ind = this.indList.get(decisionId);
		ind.setSelectedOption(optionId);

		//
		log.debug("Decision: " + ind.getIndiNodeDecision().getName());
		IndiNodeDecisionOption indo = ind.getSelectedOption();
		log.debug("New option is: " + indo.getName());
		List<IndiNode> destList = ind.getIndiNodeDecision().getDestinationNodes(IndiNodeType.VALUE);
		// Set the new sourcevalue on each value node.
		for (IndiNode valueNode : destList) {
			IndiNodeValue inv = (IndiNodeValue) valueNode;
			String selfId = inv.getNodeId();
			Float indoValue = indo.getValue(selfId);
			inv.setValue(decisionId, indoValue);
		}
		this.mainWindow.updateID();
	}

}
