package org.indival.model.stat.node;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IvNodeAlternativeTest {

    IvNodeAlternative alternative;
    
    @Before
    public void setUp() throws Exception {
	alternative = new IvNodeAlternative("nodeId");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testIvNodeAlternative() {
	assertEquals(IvNodeType.ALTERNATIVE, alternative.getType());
	assertEquals("nodeId", alternative.getIdentifier());
    }

    @Test
    public final void testGetConfiguredValueNodeIds() {
	alternative.setAttribute("name", "someName");
	alternative.setAttribute("<val1>", "1.5");
	alternative.setAttribute("<val2>", "25");
	alternative.setAttribute("<val3", "nonsense");
	Map<String, Float> retMap = alternative.getConfiguredValueNodeIds();
	assertEquals(2, retMap.keySet().size());
	assertEquals(Float.valueOf("1.5"), retMap.get("val1"));
	assertEquals(Float.valueOf("25"), retMap.get("val2"));
    }

}
