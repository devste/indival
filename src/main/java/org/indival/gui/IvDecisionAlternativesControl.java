package org.indival.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.indival.IvMainWindow;
import org.indival.model.dyn.IvNodeAlternativeDyn;
import org.indival.model.dyn.IvNodeDecisionDyn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IvDecisionAlternativesControl extends JPanel implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = -8105169920257321664L;
    private HashMap<String, IvNodeDecisionDyn> indList = new HashMap<>();
    private HashMap<String, IvNodeAlternativeDyn> alternatives = new HashMap<>();
    private IvMainWindow mainWindow;
    private Logger log = LoggerFactory.getLogger(this.getClass().toString());

    public IvDecisionAlternativesControl(List<IvNodeDecisionDyn> nodeList, IvMainWindow mainWindow) {
	super(new GridLayout(0, 1));
	this.mainWindow = mainWindow;
	for (IvNodeDecisionDyn node : nodeList) {
	    this.indList.put(node.getIdentifier(), node);
	}

	JPanel radioPanel = new JPanel(new GridLayout(0, 1));
	this.add(radioPanel);

	for (IvNodeDecisionDyn ind : this.indList.values()) {
	    JPanel jp = radioButtonPanel(ind);
	    this.add(jp);
	}

    }

    private JPanel radioButtonPanel(IvNodeDecisionDyn node) {
	JPanel radioButtonPanel = new JPanel(new GridLayout(0, 1));
	// First a label for the whole Decision
	JLabel label = new JLabel(node.toString());
	radioButtonPanel.add(label);
	// Then an option per DecisionOption
	ButtonGroup group = new ButtonGroup();
	SortedMap<String, IvNodeAlternativeDyn> alternatives = node.getAlternatives();
	for (IvNodeAlternativeDyn a : alternatives.values()) {
	    this.alternatives.put(a.getIdentifier(), a);
	    JRadioButton theButton = new JRadioButton(a.getName());
	    theButton.setActionCommand(a.getIdentifier());
	    group.add(theButton);
	    theButton.addActionListener(this);
	    radioButtonPanel.add(theButton);
	}
	return radioButtonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	log.info("Option switching command {}", command);
	IvNodeAlternativeDyn alternative = this.alternatives.get(command);
	IvNodeDecisionDyn ancestor = alternative.getAncestor();
	ancestor.setSelectedAlternative(command);
	this.mainWindow.updateID();
	// Find the alternative we want to switch on.

	// String ac = e.getActionCommand();
	// log.info("Option switching command: " + ac);
	//
	// // Set the selection
	// String[] strings = ac.split("_");
	// String decisionId = strings[0];
	// String optionId = strings[1];
	// IndiNodeDecisionOptionSelection ind = this.indList.get(decisionId);
	// ind.setSelectedOption(optionId);
	//
	// //
	// log.debug("Decision: " + ind.getIndiNodeDecision().getName());
	// IndiNodeDecisionOption indo = ind.getSelectedOption();
	// log.debug("New option is: " + indo.getName());
	// List<IndiNode> destList =
	// ind.getIndiNodeDecision().getDestinationNodes(IndiNodeType.VALUE);
	// // Set the new sourcevalue on each value node.
	// for (IndiNode valueNode : destList) {
	// IndiNodeValue inv = (IndiNodeValue) valueNode;
	// String selfId = inv.getNodeId();
	// Float indoValue = indo.getValue(selfId);
	// inv.setValue(decisionId, indoValue);
	// }
	// this.mainWindow.updateID();
    }

}
