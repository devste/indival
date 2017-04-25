package org.indival.filereader;

import java.io.IOException;
import java.io.InputStreamReader;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.indival.fileParser.indigraphLexer;
import org.indival.fileParser.indigraphParser;

public class IdgParser {

    public static indigraphParser parse(InputStreamReader sr) throws IOException {
	CodePointCharStream cs = CharStreams.fromReader(sr);
	indigraphLexer idgLexer = new indigraphLexer(cs);
	CommonTokenStream cts = new CommonTokenStream(idgLexer);
	return new indigraphParser(cts);
    }

}
