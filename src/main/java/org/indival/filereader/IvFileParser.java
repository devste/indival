package org.indival.filereader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.indival.fileParser.indigraphLexer;
import org.indival.fileParser.indigraphParser;
import org.indival.model.stat.jgrapht.IvGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class bundles methods to read an indigraph file.
 * It is a singleton, so read the instance with getInstance() first.
 */
public class IvFileParser {

    private static IvFileParser instance = null;
    private Logger log = LoggerFactory.getLogger(this.getClass().toString());

    private IvFileParser() {
    }

    public static IvFileParser getInstance() {
	if (instance == null) {
	    instance = new IvFileParser();
	}
	return instance;
    }

    private indigraphParser prepareParser(InputStreamReader sr) throws IOException {
	CodePointCharStream cs = CharStreams.fromReader(sr);
	indigraphLexer idgLexer = new indigraphLexer(cs);
	CommonTokenStream cts = new CommonTokenStream(idgLexer);
	return new indigraphParser(cts);
    }

    /*
     * 
     */
    public void parseFromResourceFile(String filename, IvGraph graph) {
	log.info("Parsing indigraph resource file " + filename);
	InputStream in = getClass().getResourceAsStream("/" + filename);
	processFile(in, graph);
    }

    private void processFile(InputStream is, IvGraph graph) {
	try {
	    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	    indigraphParser parser = prepareParser(isr);
	    indigraphParser.IndigraphContext context = parser.indigraph();
	    IvFileVisitor visitor = new IvFileVisitor(graph);
	    visitor.visit(context);
	    graph.postProcessFunctionalAlternative();
	} catch (IOException e) {
	    log.debug(e.getMessage());
	}
    }

}
