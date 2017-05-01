/**
 * 
 */
package org.indival.gui;

import java.util.ResourceBundle;

import org.indival.gui.mxgraph.Stylesheet;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 *
 */
public class MxGraphEdit {
    private mxGraph graph;
    private mxGraphModel model;
    private mxGraphComponent component;
    ResourceBundle messages;

    public MxGraphEdit(mxGraphModel model, ResourceBundle messages) {
	this.model = model;
	graph = new mxGraph(this.model);
	Stylesheet.applyStylesheet(graph);
	graph.setBorder(50);
	// graph.setEnabled(false); 		// does nothing
	// graph.setCellsBendable(false); 	// does nothing
	// graph.setCellsDisconnectable(false); // does nothing
	graph.setCellsEditable(false); 		// Locks editing the label, leave activated
	// graph.setCellsLocked(true); 		// Stops the mouse pointer from changing to editing suggestion. For layouting, cells need to be unlocked and then locked again.
	graph.setCellsResizable(false);		// Stops resizing
	// graph.setCellsSelectable(false); 	// Locks moving and resizing, leave activated
	// graph.setConnectableEdges(false); 	// does nothing
	// graph.setDropEnabled(false); 	// does nothing
	// graph.setPortsEnabled(false);	// does nothing
	this.component = new mxGraphComponent(this.graph);
	this.messages = messages;
    }

    public mxGraphComponent getComponent() {
	this.component.refresh();
	layoutCompactTree();
	return this.component;
    }

    public void layoutCompactTree() {
	// graph.setCellsLocked(false);
	mxCompactTreeLayout mxLayout = new mxCompactTreeLayout(this.graph);
	mxLayout.setResizeParent(true);
	mxLayout.setResetEdges(true);
	mxLayout.execute(this.graph.getDefaultParent());
	this.component.refresh();
	// graph.setCellsLocked(true);
    }
    
    public void layouOrganic(){
	mxOrganicLayout mxLayout = new mxOrganicLayout(this.graph);
	mxLayout.setResetEdges(true);
	mxLayout.execute(this.graph.getDefaultParent());
	this.component.refresh();
    }
    
    public void layoutFastOrganic(){
	mxFastOrganicLayout mxLayout = new mxFastOrganicLayout(this.graph);
	mxLayout.setResetEdges(true);
	mxLayout.execute(this.graph.getDefaultParent());
	this.component.refresh();
    }

}
