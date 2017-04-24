package org.indival.gui.mxgraph;

import java.util.HashMap;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class Stylesheet {

    private Stylesheet() {

    }

    public static void applyStylesheet(mxGraph graph) {
	mxStylesheet stylesheet = graph.getStylesheet();

	// Style for decision node
	HashMap<String, Object> decision = new HashMap<>();
	decision.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
	stylesheet.putCellStyle("DECISION", decision);

	// Style for uncertainty node
	HashMap<String, Object> uncertainty = new HashMap<>();
	uncertainty.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
	stylesheet.putCellStyle("UNCERTAINTY", uncertainty);

	// Style for value node
	HashMap<String, Object> value = new HashMap<>();
	value.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_HEXAGON);
	stylesheet.putCellStyle("VALUE", value);

	// Style for a decision option
	HashMap<String, Object> decisionOption = new HashMap<>();
	decisionOption.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_TRIANGLE);
	stylesheet.putCellStyle("OPTION", decisionOption);
    }

}
