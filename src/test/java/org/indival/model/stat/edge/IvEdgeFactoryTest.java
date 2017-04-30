package org.indival.model.stat.edge;

import static org.junit.Assert.*;

import org.indival.model.stat.node.IvNode;
import org.indival.model.stat.node.IvNodeBase;
import org.indival.model.stat.node.IvNodeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IvEdgeFactoryTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetEdge() {
	IvNode alternative, chance, decision, value;
	// Generate each Node Type once
	alternative = new IvNodeBase(IvNodeType.ALTERNATIVE, "node1");
	chance = new IvNodeBase(IvNodeType.CHANCE, "node2");
	decision = new IvNodeBase(IvNodeType.DECISION, "node3");
	value = new IvNodeBase(IvNodeType.VALUE, "node4");

	// Generate each type once
	IvEdge altAlt, altDecision, funcAlternative, funcDecision, infoChance, infoDecision;
	altDecision = IvEdgeFactory.getEdge(decision, alternative);
	funcAlternative = IvEdgeFactory.getEdge(alternative, value);
	funcDecision = IvEdgeFactory.getEdge(decision, value);
	infoChance = IvEdgeFactory.getEdge(chance, decision);
	infoDecision = IvEdgeFactory.getEdge(decision, decision);
	altAlt = IvEdgeFactory.getEdge(alternative, alternative);
	
	// Compare the results
	assertEquals(IvEdgeAltDecision.class, altDecision.getClass());
	assertEquals(IvEdgeFuncAlternative.class, funcAlternative.getClass());
	assertEquals(IvEdgeFuncDecision.class, funcDecision.getClass());
	assertEquals(IvEdgeInfoChance.class, infoChance.getClass());
	assertEquals(IvEdgeInfoDecision.class, infoDecision.getClass());
	
	// Some should be null
	assertNull(altAlt);
    }

}
