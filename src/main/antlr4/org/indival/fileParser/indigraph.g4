grammar indigraph;
indigraph: ((nodeDef | edgeDef) NEWLINE* )*;

nodeDef: nodeDecl ':' (NEWLINE attribute)*;
nodeDecl: nodeUncertainty | nodeDecision | nodeValue | nodeAlternative;
nodeUncertainty: '(' nodeId ')';
nodeDecision: '[' nodeId ']';
nodeValue: '<' nodeId '>';
nodeAlternative: '*' nodeId '*';
nodeId: ID;
attribute: attrName'='attrValue;
attrName: ID | nodeValue;
attrValue: VALUE;

// decisionOption: '*' decisionId '*' ':' (NEWLINE attribute)*;
// decisionId: ID;

edgeDef: edgeUncertUncert | edgeUncertDec | edgeDecDec | edgeDecValue | edgeDecAlt;
edgeUncertUncert: nodeUncertainty ' ' nodeUncertainty;
edgeUncertDec: nodeUncertainty ' ' nodeDecision;
edgeDecDec: nodeDecision ' ' nodeDecision;
edgeDecAlt: nodeDecision ' ' nodeAlternative;
edgeDecValue: nodeDecision ' ' nodeValue;

fragment ALLOWED_VALS : [ /!$%&'´?\-.];
fragment LETTERS : [a-zA-Z] ;
fragment NUMBERS : [0-9] ;
fragment UNDERSCORE : '_';

ID : ( LETTERS | NUMBERS | UNDERSCORE )+;
VALUE : ( LETTERS | NUMBERS | ALLOWED_VALS | '\u0080' .. '\ufffd' )+;
NEWLINE : ('\r' | '\n' | '\r\n');
WHITESPACE : [ \t]+ -> skip;
