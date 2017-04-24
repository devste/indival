package org.indival.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndiNodeBaseTest {

    IndiNodeBase empty, nodeIdOnly, nodeIdType, nodeIdTypeName;

    @Before
    public void setUp() throws Exception {
	empty = new IndiNodeBase();
	nodeIdOnly = new IndiNodeBase("nodeIdOnlyNodeId");
	nodeIdType = new IndiNodeBase("nodeIdTypeNodeId", IndiNodeType.DECISION);
	nodeIdTypeName = new IndiNodeBase("nodeIdTypeNameNodeId", IndiNodeType.VALUE, "nodeIdTypeNameName");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetType() {
	assertNull(empty.getType());
	assertNull(nodeIdOnly.getType());
	assertEquals(IndiNodeType.DECISION, nodeIdType.getType());
	assertEquals(IndiNodeType.VALUE, nodeIdTypeName.getType());
    }

    @Test
    public final void testGetName() {
	assertEquals("", empty.getName());
	assertEquals("", nodeIdOnly.getName());
	assertEquals("", nodeIdType.getName());
	assertEquals("nodeIdTypeNameName", nodeIdTypeName.getName());
    }

    @Test
    public final void testToString() {
	assertEquals("", empty.toString());
	assertEquals("", nodeIdOnly.toString());
	assertEquals("", nodeIdType.toString());
	assertEquals("nodeIdTypeNameName", nodeIdTypeName.toString());
    }

    @Test
    public final void testSetName() {
	assertEquals("", empty.getName());
	empty.setName("testName");
	assertEquals("testName", empty.getName());
    }

    @Test
    public final void testAddDestinationNode() {
	assertFalse(empty.isDestinationNode(nodeIdOnly.getNodeId()));
	empty.addDestinationNode(nodeIdOnly);
	assertTrue(empty.isDestinationNode(nodeIdOnly.getNodeId()));
    }

    @Test
    public final void testIsDestinationNode() {
	assertFalse(empty.isDestinationNode(""));
	assertFalse(empty.isDestinationNode("somestring"));
    }

    @Test
    public final void testGetNodeId() {
	assertEquals("", empty.getNodeId());
	assertEquals("nodeIdOnlyNodeId", nodeIdOnly.getNodeId());
	assertEquals("nodeIdTypeNodeId", nodeIdType.getNodeId());
	assertEquals("nodeIdTypeNameNodeId", nodeIdTypeName.getNodeId());
    }

    @Test
    public final void testGetDestinationNodes() {
	assertFalse(empty.isDestinationNode("testNodeId"));
	empty.addDestinationNode(nodeIdOnly);
	empty.addDestinationNode(nodeIdType);
	empty.addDestinationNode(nodeIdTypeName);
	List<IndiNode> ret1, ret2, ret3, ret4;
	ret1 = empty.getDestinationNodes(null);
	ret2 = empty.getDestinationNodes(IndiNodeType.DECISION);
	ret3 = empty.getDestinationNodes(IndiNodeType.VALUE);
	ret4 = empty.getDestinationNodes(IndiNodeType.UNCERTAINTY);
	assertEquals(1, ret1.size());
	assertEquals(1, ret2.size());
	assertEquals(1, ret3.size());
	assertEquals(0, ret4.size());
	assertEquals("nodeIdOnlyNodeId", ret1.get(0).getNodeId());
	assertEquals("nodeIdTypeNodeId", ret2.get(0).getNodeId());
	assertEquals("nodeIdTypeNameNodeId", ret3.get(0).getNodeId());
    }

}
