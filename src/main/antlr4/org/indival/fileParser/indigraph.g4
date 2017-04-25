grammar indigraph;
indigraph: ((nodeDef | edgeDef) NEWLINE* )*;

nodeDef: nodeDecl ':' (NEWLINE attribute)* (NEWLINE+ decisionOption)*;
nodeDecl: nodeUncertainty | nodeDecision | nodeValue;
nodeUncertainty: '(' nodeId ')';
nodeDecision: '[' nodeId ']';
nodeValue: '<' nodeId '>';
nodeId: ID;
attribute: attrName'='attrValue;
attrName: ID | nodeValue;
attrValue: VALUE;

decisionOption: '*' decisionId '*' ':' (NEWLINE attribute)*;
decisionId: ID;

edgeDef: edgeUncertUncert | edgeUncertDec | edgeDecDec | edgeDecValue;
edgeUncertUncert: nodeUncertainty ' ' nodeUncertainty;
edgeUncertDec: nodeUncertainty ' ' nodeDecision;
edgeDecDec: nodeDecision ' ' nodeDecision;
edgeDecValue: nodeDecision ' ' nodeValue;

fragment ALLOWED_VALS : [\ \/!$%&'´?\-\.];
fragment LETTERS : [a-zA-Z] ;
fragment NUMBERS : [0-9] ;

ID : ( LETTERS | NUMBERS )+;
VALUE : ( LETTERS | NUMBERS | ALLOWED_VALS | '\u0080' .. '\ufffd' )+;
NEWLINE : [\r\n];
WHITESPACE : [ \t]+ -> skip;
