package org.indival.model.stat.node;

import static org.junit.Assert.*;

import org.indival.model.dyn.IvNodeDecisionDyn;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IvNodeFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetNode() {
	IvNode alternative = IvNodeFactory.getNode(IvNodeType.ALTERNATIVE, "id1");
	assertEquals(IvNodeAlternative.class, alternative.getClass());
	IvNode chance = IvNodeFactory.getNode(IvNodeType.CHANCE, "id2");
	assertEquals(IvNodeChance.class, chance.getClass());
	IvNode decision = IvNodeFactory.getNode(IvNodeType.DECISION, "id3");
	assertEquals(IvNodeDecisionDyn.class, decision.getClass());
	IvNode value = IvNodeFactory.getNode(IvNodeType.VALUE, "id4");
	assertEquals(IvNodeValue.class, value.getClass());
    }

}
