package org.indival.model.stat;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IvEdgeBaseTest {
    
    IvEdge infoChance, infoDecision, funcAlternative, funcDecision, altDecision;

    @Before
    public void setUp() throws Exception {
	// Generate each Node Type once
	IvNode decision = new IvNodeBase(IvNodeType.DECISION);
	IvNode value = new IvNodeBase(IvNodeType.VALUE);
	IvNode chance = new IvNodeBase(IvNodeType.CHANCE);
	IvNode alternative = new IvNodeBase(IvNodeType.ALTERNATIVE);
	
	infoChance = new IvEdgeBase(chance, decision);
	infoDecision = new IvEdgeBase(decision, decision);
	funcDecision = new IvEdgeBase(decision, value);
	funcAlternative = new IvEdgeBase(alternative, value);
	altDecision = new IvEdgeBase(decision, alternative);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetEdgeType() {
	assertEquals(IvEdgeType.INFORMATIONAL.CHANCE, infoChance.getEdgeType());
	assertEquals(IvEdgeType.INFORMATIONAL.DECISION, infoDecision.getEdgeType());
	assertEquals(IvEdgeType.FUNCTIONAL.ALTERNATIVE, funcAlternative.getEdgeType());
	assertEquals(IvEdgeType.FUNCTIONAL.DECISION, funcDecision.getEdgeType());
	assertEquals(IvEdgeType.ALTERNATIVE.DECISION, altDecision.getEdgeType());
    }

}
