package org.indival;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuActions implements ActionListener {
    private IvMainWindow parent;
    
    public MainMenuActions(IvMainWindow parent){
	this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	case "showIDEdit":
		parent.showIDEdit();
		break;
	case "layoutCompactTree":
		parent.layoutCompactTree();
		break;
	case "layoutOrganic":
		parent.layoutOrganic();
		break;
	case "layoutFastOrganic":
	    	parent.layoutFastOrganic();
	    	break;
	case "projectOpen":
		parent.openProjectFile();
		break;
	case "projectReload":
		parent.reloadFromFile();
		break;
	case "projectExampleCar":
		parent.openExampleCar();
		break;
	case "projectExampleSoftware":
		parent.openExampleSoftware();
		break;
	case "updateID":
		parent.updateID();
		break;
	case "exit":
		parent.exit();
		break;
	default:
		break;
	}

    }

}
