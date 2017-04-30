package org.indival.model.stat;

/*
 * The central enum declaring all possible IvNode types.
 * Mostly an IvNodeType also has it's own subclassed implementation.
 */
public enum IvNodeType {
    /*
     * The first three types are node types in an influence diagramme.
     */
    CHANCE, DECISION, VALUE,
    /*
     * The next is an alternative on a decision. It is used as a node type in
     * Indival.
     */
    ALTERNATIVE
}
