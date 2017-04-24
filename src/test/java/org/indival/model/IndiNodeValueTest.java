package org.indival.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndiNodeValueTest {
    
    IndiNodeValue empty;

    @Before
    public void setUp() throws Exception {
	empty = new IndiNodeValue("emptyNodeId");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testToString() {
	assertEquals(": 0.0 ", empty.toString());
    }

    @Test
    public final void testSetValue() {
	assertEquals(Float.valueOf("0"), empty.calculateCurrentValue());
	empty.setValue("node1", Float.valueOf("1"));
	assertEquals(Float.valueOf("1"), empty.calculateCurrentValue());
	empty.setValue("node1", Float.valueOf("2"));
	assertEquals(Float.valueOf("2"), empty.calculateCurrentValue());
	empty.setValue("node2", Float.valueOf("2"));
	assertEquals(Float.valueOf("4"), empty.calculateCurrentValue());
    }

    @Test
    public final void testCalculateCurrentValue() {
	assertEquals(Float.valueOf("0"), empty.calculateCurrentValue());
    }

    @Test
    public final void testGetUnit() {
	assertEquals("", empty.getUnit());
    }

    @Test
    public final void testSetUnit() {
	assertEquals("", empty.getUnit());
	empty.setUnit("CHF");
	assertEquals("CHF", empty.getUnit());
	empty.setUnit("KCHF");
	assertEquals("KCHF", empty.getUnit());
    }

}
