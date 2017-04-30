package org.indival.model;

import org.indival.model.stat.jgrapht.IvGraph;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

/*
 * This class pulls together some references, so the GUI can refer to these
 */
public class IvProject {
    private IvGraph staticGraph = new IvGraph();
    private IvGraph dynamicGraph = new IvGraph();
    private mxGraphModel staticMxgModel = new mxGraphModel();
    private mxGraphModel dynamicMxgModel = new mxGraphModel();
    private mxGraph staticMxGraph = new mxGraph(staticMxgModel);
    private mxGraph dynamicMxGraph = new mxGraph(dynamicMxgModel);

    /**
     * @return the staticGraph
     */
    public IvGraph getStaticGraph() {
	return staticGraph;
    }

    /**
     * @return the dynamicGraph
     */
    public IvGraph getDynamicGraph() {
	return dynamicGraph;
    }

    /**
     * @return the staticMxGraph
     */
    public mxGraph getStaticMxGraph() {
	return staticMxGraph;
    }

    /**
     * @return the dynamicMxGraph
     */
    public mxGraph getDynamicMxGraph() {
	return dynamicMxGraph;
    }

    /**
     * @return the staticMxgModel
     */
    public mxGraphModel getStaticMxgModel() {
	return staticMxgModel;
    }

    /**
     * @return the dynamicMxgModel
     */
    public mxGraphModel getDynamicMxgModel() {
	return dynamicMxgModel;
    }
    
    public void updateMxGraph(){
	staticGraph.toMxGraph(staticMxGraph, staticMxgModel);
    }

}
