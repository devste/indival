/**
 * 
 */
package org.indival.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import org.indival.gui.mxgraph.Stylesheet;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 *
 */
public class MxGraphEdit implements ActionListener {
    private mxGraph graph;
    private mxGraphModel model;
    private mxGraphComponent component;
    ResourceBundle messages;

    public MxGraphEdit(mxGraphModel model, ResourceBundle messages) {
	this.model = model;
	graph = new mxGraph(this.model);
	Stylesheet.applyStylesheet(graph);
	this.component = new mxGraphComponent(this.graph);
	this.messages = messages;
    }

    public mxGraphComponent getComponent() {
	this.component.refresh();
	applyLayout();
	return this.component;
    }

    public void applyLayout() {
	mxCompactTreeLayout mxLayout = new mxCompactTreeLayout(this.graph);
	mxLayout.execute(this.graph.getDefaultParent());
	this.component.refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	case "id.showDetails":
	    // TODO some action
	    break;
	}

    }
}
