package org.indival.filereader;

import java.util.List;

// import org.antlr.v4.runtime.*;
// import org.antlr.v4.runtime.misc.*;
// import org.antlr.v4.runtime.tree.*;
import org.example.idifosop.fileParser.indigraphBaseVisitor;
import org.example.idifosop.fileParser.indigraphParser;
import org.example.idifosop.fileParser.indigraphParser.AttributeContext;
import org.example.idifosop.fileParser.indigraphParser.DecisionOptionContext;
import org.example.idifosop.fileParser.indigraphParser.EdgeDefContext;
import org.example.idifosop.fileParser.indigraphParser.NodeDeclContext;
import org.example.idifosop.fileParser.indigraphParser.NodeDefContext;
import org.example.idifosop.fileParser.indigraphParser.NodeValueContext;
import org.indival.model.IndiNode;
import org.indival.model.IndiNodeBase;
import org.indival.model.IndiNodeDecision;
import org.indival.model.IndiNodeDecisionOption;
import org.indival.model.IndiNodeType;
import org.indival.model.IndiNodeValue;
import org.indival.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

/*
 * This class implements a visitor to transform 
 * an indigraph project file to an mxGraph.
 */
public class IdgFileVisitor extends indigraphBaseVisitor<String> {

    private Project project;
    private mxGraphModel model;
    private mxGraph graph;
    private Object parent;
    private Logger log = LoggerFactory.getLogger(this.getClass().toString());

    public IdgFileVisitor(Project project) {
	this.project = project;
	this.model = project.getModel();
	this.graph = new mxGraph(this.model);
	this.parent = graph.getDefaultParent();
    }

    /*
     * First, we iterate through all node definitions.
     * 
     * Then we add the edges.
     * 
     * Finally we iterate through all node definitions again, so value
     * definitions can be checked against edges.
     */

    @Override
    public String visitIndigraph(indigraphParser.IndigraphContext ctx) {
	List<NodeDefContext> ndlist = ctx.nodeDef();
	List<EdgeDefContext> edlist = ctx.edgeDef();
	for (NodeDefContext ndc : ndlist) {
	    visitNodeDef(ndc);
	}
	for (EdgeDefContext edc : edlist) {
	    visitEdgeDef(edc);
	}
	for (NodeDefContext ndc : ndlist) {
	    visitNodeDef(ndc);
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.example.idifosop.fileParser.indigraphBaseVisitor#visitNodeDef(org.
     * example.idifosop.fileParser.indigraphParser.NodeDefContext)
     * 
     * In a NodeDefintion we must make sure we visit the nodeDeclarations first,
     * so we have the nodes before adding attributes to the nodes.
     * 
     * This is also the only place where to add attributes to a node.
     */
    @Override
    public String visitNodeDef(indigraphParser.NodeDefContext ctx) {
	NodeDeclContext ndc = ctx.nodeDecl();
	String nodeId = visitNodeDecl(ndc);
	IndiNode node = this.project.getNode(nodeId);
	List<AttributeContext> acList = ctx.attribute();
	for (AttributeContext ac : acList) {
	    String name = ac.attrName().getText();
	    String value = ac.attrValue().getText();
	    if ("name".equals(name)) {
		log.debug("Setting name on node " + nodeId + ": " + value);
		node.setName(value);
	    }
	}
	// If it's a decision node, we will have to process the decision options
	if (node.getType() == IndiNodeType.DECISION) {
	    IndiNodeDecision ind = (IndiNodeDecision) node;
	    List<DecisionOptionContext> doList = ctx.decisionOption();
	    for (DecisionOptionContext doc : doList) {
		processDecisionOption(ind, doc);
	    }
	}
	// add all nodes to mxGraph
	String nodeType = node.getType().toString();
	if (this.model.getCell(nodeId) == null) {
	    this.graph.insertVertex(this.parent, nodeId, node, 100, 100, 100, 50, nodeType);
	}
	return nodeId;
    }

    private void processDecisionOption(IndiNodeDecision ind, DecisionOptionContext doc) {
	String key = doc.decisionId().getText();
	IndiNodeDecisionOption indo = new IndiNodeDecisionOption(ind.getNodeId() + "_" + key, ind);
	List<AttributeContext> acList = doc.attribute();
	for (AttributeContext ac : acList) {
	    String name = ac.attrName().getText();
	    String value = ac.attrValue().getText();
	    if ("name".equals(name)) {
		indo.setName(value);
	    }
	    // We pick out value nodes in the attributes
	    NodeValueContext nvc = ac.attrName().nodeValue();
	    if (nvc != null) {
		String nodeValueName = nvc.nodeId().getText();
		indo.setValue(nodeValueName, Float.valueOf(value));
	    }
	}
	ind.putDecisionOption(key, indo);
	log.debug("Added decision option: " + indo.toString());
    }

    @Override
    public String visitNodeUncertainty(indigraphParser.NodeUncertaintyContext ctx) {
	String nodeId = ctx.nodeId().getText();
	IndiNode in = createNode(nodeId, IndiNodeType.UNCERTAINTY);
	visitNode(nodeId, in);
	return nodeId;
    }

    @Override
    public String visitNodeDecision(indigraphParser.NodeDecisionContext ctx) {
	String nodeId = ctx.nodeId().getText();
	IndiNode in = createNode(nodeId, IndiNodeType.DECISION);
	visitNode(nodeId, in);
	return nodeId;
    }

    @Override
    public String visitNodeValue(indigraphParser.NodeValueContext ctx) {
	String nodeId = ctx.nodeId().getText();
	IndiNode in = createNode(nodeId, IndiNodeType.VALUE);
	visitNode(nodeId, in);
	return nodeId;
    }

    /*
     * Creates a new node of a type or returns an existing node.
     */
    private IndiNode createNode(String nodeId, IndiNodeType type) {
	IndiNode in = project.getNode(nodeId);
	if (in == null) {
	    switch (type) {
	    case DECISION:
		in = new IndiNodeDecision(nodeId);
		break;
	    case VALUE:
		in = new IndiNodeValue(nodeId);
		break;
	    default:
		in = new IndiNodeBase(nodeId, type);
	    }
	    log.info("New NODE: " + nodeId + " (" + type.toString() + ")");
	}
	return in;
    }

    private void visitNode(String nodeId, IndiNode in) {
	this.project.addNodeToList(nodeId, in);
    }

    @Override
    public String visitEdgeUncertUncert(indigraphParser.EdgeUncertUncertContext ctx) {
	String sourceName = ctx.nodeUncertainty(0).nodeId().getText();
	String destName = ctx.nodeUncertainty(1).nodeId().getText();
	edgeVisit("Uncert -> Uncert", sourceName, destName);
	return null;
    }

    @Override
    public String visitEdgeUncertDec(indigraphParser.EdgeUncertDecContext ctx) {
	String sourceName = ctx.nodeUncertainty().nodeId().getText();
	String destName = ctx.nodeDecision().nodeId().getText();
	edgeVisit("Uncert -> Decision", sourceName, destName);
	return null;
    }

    @Override
    public String visitEdgeDecDec(indigraphParser.EdgeDecDecContext ctx) {
	String sourceName = ctx.nodeDecision(0).nodeId().getText();
	String destName = ctx.nodeDecision(1).nodeId().getText();
	edgeVisit("Decision -> Decision", sourceName, destName);
	return null;
    }

    @Override
    public String visitEdgeDecValue(indigraphParser.EdgeDecValueContext ctx) {
	String sourceName = ctx.nodeDecision().nodeId().getText();
	String destName = ctx.nodeValue().nodeId().getText();
	edgeVisit("Decision -> Value", sourceName, destName);
	return null;
    }

    private void edgeVisit(String edgeType, String sourceName, String destName) {
	log.info("Visiting edge " + edgeType + ": " + sourceName + " -> " + destName);
	IndiNode sourceNode = project.getNode(sourceName);
	IndiNode destNode = project.getNode(destName);
	sourceNode.addDestinationNode(destNode);
	// Do stuff in mxGraph
	Object sourceCell = this.model.getCell(sourceName);
	Object destCell = this.model.getCell(destName);
	this.graph.insertEdge(this.parent, sourceName + "->" + destName, "", sourceCell, destCell);
    }

}
