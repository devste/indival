package org.indival.filereader;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.example.idifosop.fileParser.indigraphLexer;
import org.example.idifosop.fileParser.indigraphParser;

public class IdgParser {

    private IdgParser() {
    }

    public static indigraphParser parse(InputStream is) throws IOException {
	ANTLRInputStream ais = new ANTLRInputStream(is);
	indigraphLexer idgLexer = new indigraphLexer(ais);
	CommonTokenStream cts = new CommonTokenStream(idgLexer);
	return new indigraphParser(cts);
    }

}
