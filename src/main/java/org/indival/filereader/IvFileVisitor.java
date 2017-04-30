package org.indival.filereader;

import java.util.HashMap;
import java.util.List;

import org.indival.fileParser.indigraphBaseVisitor;
import org.indival.fileParser.indigraphParser;
import org.indival.fileParser.indigraphParser.AttributeContext;
import org.indival.fileParser.indigraphParser.EdgeDefContext;
import org.indival.fileParser.indigraphParser.NodeDeclContext;
import org.indival.fileParser.indigraphParser.NodeDefContext;
import org.indival.model.stat.edge.IvEdge;
import org.indival.model.stat.edge.IvEdgeBase;
import org.indival.model.stat.jgrapht.IvGraph;
import org.indival.model.stat.node.IvNode;
import org.indival.model.stat.node.IvNodeFactory;
import org.indival.model.stat.node.IvNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IvFileVisitor extends indigraphBaseVisitor<String> {

    private Logger log = LoggerFactory.getLogger(this.getClass().toString());
    // While visiting the file we need to keep track of the nodeId and map them
    // to the objects.
    private HashMap<String, IvNode> nodeMap = new HashMap<>();
    private IvGraph graph;

    public IvFileVisitor(IvGraph graph) {
	this.graph = graph;
    }

    /*
     * This method returns an IvNode, if it exists in the map.
     */
    private IvNode getNode(String nodeId) {
	if (nodeMap.containsKey(nodeId)) {
	    return nodeMap.get(nodeId);
	} else {
	    return null;
	}
    }

    /*
     * This method will make a new node with the nodeId or return the node if a
     * node with the same id exists already.
     */
    private IvNode makeNode(String nodeId, IvNodeType type) {
	IvNode ivnode;
	ivnode = getNode(nodeId);
	if ((ivnode != null) && (ivnode.getType() != type)) {
	    log.warn("Trying to redeclare node with identifier {} to new type {}", nodeId, type);
	    // TODO: throw an exception
	}
	if (ivnode == null) {
	    log.trace("Creating new node with nodeId {} and type {}", nodeId, type);
	    ivnode = IvNodeFactory.getNode(type, nodeId);
	    this.nodeMap.put(nodeId, ivnode);
	    boolean ret = this.graph.addVertex(ivnode);
	    if (ret) {
		log.debug("successfully added node {} to graph", ivnode);
	    }
	}
	return ivnode;
    }

    /*
     * This will add an edge to the graph.
     */
    private void addEdge(String sourceNodeId, String destNodeId) {
	IvNode source = getNode(sourceNodeId);
	if (source == null) {
	    // TODO exception
	    log.warn("node for nodeId %s doesn't exist", sourceNodeId);
	}
	IvNode dest = getNode(destNodeId);
	if (dest == null) {
	    // TODO exception
	    log.warn("node for nodeId %s doesn't exist", destNodeId);
	}
	IvEdge edge = new IvEdgeBase(source, dest);
	if (edge.getEdgeType() == null) {
	    log.warn("Edge from nodeType {} to nodeType {} is not valid in indival", source.getType(), dest.getType());
	} else {
	    boolean ret = this.graph.addEdge(source, dest, edge);
	    if (ret) {
		log.debug("successfully added edge {} to graph", edge);
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.indival.fileParser.indigraphBaseVisitor#visitIndigraph(org.indival.
     * fileParser.indigraphParser.IndigraphContext)
     * 
     * Visit all the nodeDefinitions first, then visit all the edgeDefinitions
     */
    @Override
    public String visitIndigraph(indigraphParser.IndigraphContext ctx) {
	List<NodeDefContext> ndlist = ctx.nodeDef();
	List<EdgeDefContext> edlist = ctx.edgeDef();
	log.trace("Visiting all nodes");
	for (NodeDefContext ndc : ndlist) {
	    visitNodeDef(ndc);
	}
	log.trace("Visiting all edges");
	for (EdgeDefContext edc : edlist) {
	    visitEdgeDef(edc);
	}
	return null;
    }

    @Override
    public String visitNodeDef(indigraphParser.NodeDefContext ctx) {
	NodeDeclContext ndc = ctx.nodeDecl();
	// First the node should be created by visitNodeDecl, which in turn
	// visits each nodeType, where the visitor of each nodeType creates the
	// node
	String nodeId = visitNodeDecl(ndc);
	log.debug("visitNodeDef: nodeId is {}", nodeId);
	// Then we get the node
	IvNode ivnode = getNode(nodeId);
	log.debug("visitNodeDef: node is {}", ivnode);
	// Then we add all attributes to a node
	if (ivnode != null) {
	    List<AttributeContext> acList = ctx.attribute();
	    for (AttributeContext ac : acList) {
		String name = ac.attrName().getText();
		String value = ac.attrValue().getText();
		log.debug("Node {}. Adding Attribute: ({} -> {})", ivnode, name, value);
		ivnode.setAttribute(name, value);
	    }
	}
	return null;
    }

    @Override
    public String visitNodeUncertainty(indigraphParser.NodeUncertaintyContext ctx) {
	String nodeId = ctx.nodeId().getText();
	makeNode(nodeId, IvNodeType.CHANCE);
	return nodeId;
    }

    @Override
    public String visitNodeDecision(indigraphParser.NodeDecisionContext ctx) {
	String nodeId = ctx.nodeId().getText();
	makeNode(nodeId, IvNodeType.DECISION);
	return nodeId;
    }

    @Override
    public String visitNodeValue(indigraphParser.NodeValueContext ctx) {
	String nodeId = ctx.nodeId().getText();
	makeNode(nodeId, IvNodeType.VALUE);
	return nodeId;
    }

    @Override
    public String visitNodeAlternative(indigraphParser.NodeAlternativeContext ctx) {
	String nodeId = ctx.nodeId().getText();
	makeNode(nodeId, IvNodeType.ALTERNATIVE);
	return nodeId;
    }

    @Override
    public String visitEdgeUncertUncert(indigraphParser.EdgeUncertUncertContext ctx) {
	String sourceId = ctx.nodeUncertainty(0).nodeId().getText();
	String destId = ctx.nodeUncertainty(1).nodeId().getText();
	addEdge(sourceId, destId);
	return null;
    }

    @Override
    public String visitEdgeUncertDec(indigraphParser.EdgeUncertDecContext ctx) {
	String sourceId = ctx.nodeUncertainty().nodeId().getText();
	String destId = ctx.nodeDecision().nodeId().getText();
	addEdge(sourceId, destId);
	return null;
    }

    @Override
    public String visitEdgeDecDec(indigraphParser.EdgeDecDecContext ctx) {
	String sourceId = ctx.nodeDecision(0).nodeId().getText();
	String destId = ctx.nodeDecision(1).nodeId().getText();
	addEdge(sourceId, destId);
	return null;
    }

    @Override
    public String visitEdgeDecValue(indigraphParser.EdgeDecValueContext ctx) {
	String sourceId = ctx.nodeDecision().nodeId().getText();
	String destId = ctx.nodeValue().nodeId().getText();
	addEdge(sourceId, destId);
	return null;
    }
    
    @Override
    public String visitEdgeDecAlt(indigraphParser.EdgeDecAltContext ctx){
	String sourceId = ctx.nodeDecision().nodeId().getText();
	String destId = ctx.nodeAlternative().nodeId().getText();
	addEdge(sourceId, destId);
	return null;
    }

}
