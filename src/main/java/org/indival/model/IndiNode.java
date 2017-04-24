package org.indival.model;

import java.util.List;

public interface IndiNode {

    public IndiNodeType getType();

    public String getName();

    public void setName(String name);

    public String getLabel();

    public boolean addDestinationNode(IndiNode value);

    public boolean isDestinationNode(String nodeId);

    public String getNodeId();

    public List<IndiNode> getDestinationNodes(IndiNodeType type);

}
