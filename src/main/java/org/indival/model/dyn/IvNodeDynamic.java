package org.indival.model.dyn;

import org.indival.model.stat.jgrapht.IvGraph;

/*
 * An interface for all dynamic nodes.
 */
public interface IvNodeDynamic {

    /*
     * Usually dynamic nodes will query the graph themselves, so need a method
     * to set the graph.
     */
    public void init(IvGraph graph);

    /*
     * They should all offer an update() method to (re)-initialise the data from
     * static settings. This should not reset any local changes.
     */
    public void update();

}
