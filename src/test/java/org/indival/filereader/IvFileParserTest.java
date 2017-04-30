package org.indival.filereader;

import static org.junit.Assert.assertEquals;

import org.indival.model.stat.jgrapht.IvGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IvFileParserTest {

    IvFileParser ivfileparser;
    IvGraph graph;

    @Before
    public void setUp() throws Exception {
	ivfileparser = IvFileParser.getInstance();
	graph = new IvGraph();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void test() {
	String filename = "example-software.txt";
	ivfileparser.parseFromResourceFile(filename, graph);
	System.out.println("Parser output: " + graph.toString());
	assertEquals(13, graph.vertexSet().size());
	assertEquals(8, graph.edgeSet().size());
    }

}
