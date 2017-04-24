package org.indival.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndiNodeBase implements IndiNode, Serializable {

    private static final long serialVersionUID = 2812324065783769712L;
    private String nodeId = "";
    private IndiNodeType type;
    private String name = "";
    private HashMap<String, IndiNode> destinationNodes = new HashMap<>();

    public IndiNodeBase() {
    }

    public IndiNodeBase(String nodeId) {
	this();
	this.nodeId = nodeId;
    }

    public IndiNodeBase(String nodeId, IndiNodeType type) {
	this(nodeId);
	this.type = type;
    }

    public IndiNodeBase(String nodeId, IndiNodeType type, String name) {
	this(nodeId, type);
	this.name = name;
    }

    /**
     * @return the type
     */
    public IndiNodeType getType() {
	return type;
    }

    /**
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    @Override
    public String toString() {
	return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    @Override
    public void setName(String name) {
	this.name = name;
    }

    // @Override
    // public HashMap<String, IndiNode> getDestinationNodes() {
    // return this.destinationNodes;
    // }

    @Override
    public boolean addDestinationNode(IndiNode in) {
	String nodeId = in.getNodeId();
	if (!this.destinationNodes.containsKey(nodeId)) {
	    this.destinationNodes.put(nodeId, in);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean isDestinationNode(String nodeId) {
	return this.destinationNodes.containsKey(nodeId);
    }

    @Override
    public String getNodeId() {
	return this.nodeId;
    }

    @Override
    public List<IndiNode> getDestinationNodes(IndiNodeType type) {
	List<IndiNode> list = new ArrayList<>();
	for (IndiNode in : this.destinationNodes.values()) {
	    if (in.getType() == type) {
		list.add(in);
	    }
	}
	return list;
    }

}
