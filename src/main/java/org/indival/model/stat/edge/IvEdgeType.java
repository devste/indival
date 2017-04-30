package org.indival.model.stat.edge;

/*
 * The central enum declaring all possible IvEdgeTypes.
 * IvEdgeTypes are hierarchical, as we need to select only some specific types.
 * Basic idea of hierarchical enums from 
 * http://stackoverflow.com/questions/38393692/hierarchical-enum-in-java#38394061
 */
public enum IvEdgeType {
    /*
     * The first three types are from Influence Diagrammes. Each type is
     * subtyped to further specify it's source node. We are very selective about
     * which kind of edge to use.
     * 
     * In Indival there are no conditional arcs.
     */
    /*
     * Informational arcs end in decision nodes.
     */
    INFORMATIONAL_CHANCE, INFORMATIONAL_DECISION,

    /*
     * Functional arcs end in value nodes. In Indival only a decision node (or
     * alternative node as an extension) is able to end in a value node.
     */
    FUNCTIONAL_DECISION, FUNCTIONAL_ALTERNATIVE,

    /*
     * In Indival an alternative edge is one ending in an alternative node. Only
     * decision nodes are allowed to end in alternative nodes.
     */
    ALTERNATIVE_DECISION

}
