package org.indival.model;

import org.indival.model.stat.jgrapht.IvGraph;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

/*
 * This class pulls together some references, so the GUI can refer to these
 */
public class IvProject {
    private IvGraph graph = new IvGraph();
    private mxGraphModel mxgModel = new mxGraphModel();
    private mxGraph mxGraph = new mxGraph(mxgModel);

    /**
     * @return the graph
     */
    public IvGraph getStaticGraph() {
	return graph;
    }

    /**
     * @return the mxGraph
     */
    public mxGraph getStaticMxGraph() {
	return mxGraph;
    }

    /**
     * @return the mxgModel
     */
    public mxGraphModel getStaticMxgModel() {
	return mxgModel;
    }
    
    public void updateMxGraph(){
	graph.toMxGraph(mxGraph, mxgModel);
    }

}
