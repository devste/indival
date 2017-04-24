/**
 * 
 */
package org.indival.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;
import com.mxgraph.model.mxGraphModel;

/**
 *
 */
public class Project implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1151521530831182505L;
    private String name;
    private Map<String, IndiNode> nodeList = new HashMap<>();
    private mxGraphModel model = new mxGraphModel();

    public Project() {
	name = "not set";
	mxCodecRegistry.addPackage("org.example.idifosop.model");
	mxCodecRegistry.register(new mxObjectCodec(new org.indival.model.IndiNodeBase()));
    }

    public Project(mxGraphModel model) {

    }

    /**
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the reList
     */
    public Map<String, IndiNode> getNodeList() {
	return this.nodeList;
    }

    /**
     * @param reList
     *            the reList to set
     */
    public void setNodeList(Map<String, IndiNode> nodeList) {
	this.nodeList = nodeList;
    }

    public boolean addNodeToList(String key, IndiNode value) {
	if (!this.nodeList.containsKey(key)) {
	    this.nodeList.put(key, value);
	    return true;
	} else {
	    return false;
	}
    }

    public boolean replaceNode(String key, IndiNode value) {
	if (this.nodeList.containsKey(key)) {
	    this.nodeList.put(key, value);
	    return true;
	} else {
	    return false;
	}
    }

    public IndiNode getNode(String key) {
	return this.nodeList.get(key);
    }

    /**
     * @return the graph
     */
    public mxGraphModel getModel() {
	return this.model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(mxGraphModel model) {
	this.model = model;
    }

    public List<IndiNodeDecision> getDecisionNodeList() {
	List<IndiNodeDecision> ret = new ArrayList<>();
	for (IndiNode in : this.nodeList.values()) {
	    if (in instanceof IndiNodeDecision) {
		ret.add((IndiNodeDecision) in);
	    }
	}
	return ret;
    }

}
