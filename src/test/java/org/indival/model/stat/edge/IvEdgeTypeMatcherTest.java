package org.indival.model.stat.edge;

import static org.junit.Assert.*;

import org.indival.model.stat.edge.IvEdgeType;
import org.indival.model.stat.edge.IvEdgeTypeMatcher;
import org.indival.model.stat.node.IvNodeType;
import org.junit.Test;

public class IvEdgeTypeMatcherTest {

    @Test
    public final void test() {
	// Test some valid combinations first
	assertEquals(IvEdgeType.INFORMATIONAL_CHANCE,
		IvEdgeTypeMatcher.matcher(IvNodeType.CHANCE, IvNodeType.DECISION));
	assertEquals(IvEdgeType.INFORMATIONAL_DECISION,
		IvEdgeTypeMatcher.matcher(IvNodeType.DECISION, IvNodeType.DECISION));
	assertEquals(IvEdgeType.FUNCTIONAL_ALTERNATIVE,
		IvEdgeTypeMatcher.matcher(IvNodeType.ALTERNATIVE, IvNodeType.VALUE));
	assertEquals(IvEdgeType.FUNCTIONAL_DECISION, IvEdgeTypeMatcher.matcher(IvNodeType.DECISION, IvNodeType.VALUE));
	assertEquals(IvEdgeType.ALTERNATIVE_DECISION,
		IvEdgeTypeMatcher.matcher(IvNodeType.DECISION, IvNodeType.ALTERNATIVE));
	// Test some invalid combinations later (not all of them, though).
	assertNull(IvEdgeTypeMatcher.matcher(IvNodeType.CHANCE, IvNodeType.CHANCE));
	assertNull(IvEdgeTypeMatcher.matcher(IvNodeType.DECISION, IvNodeType.CHANCE));
	assertNull(IvEdgeTypeMatcher.matcher(IvNodeType.CHANCE, IvNodeType.VALUE));
    }

}
