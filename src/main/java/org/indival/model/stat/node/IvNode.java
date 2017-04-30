package org.indival.model.stat.node;

/*
 * Any node in Indival should implement this interface.
 * A node contains a list of attributes.
 */
public interface IvNode {

    /*
     * Return the type of this Node
     */
    public IvNodeType getType();

    /*
     * Return a unique Identifier. Not named getID() to prevent conflict with
     * jgraphx
     */
    public String getIdentifier();

    /*
     * Return the name. This should be something a users sees (like a label).
     * This should always return a String. If there is no name, return an empty
     * String.
     */
    public String getName();

    /*
     * Set an attribute
     */
    public void setAttribute(String key, String value);

    /*
     * Get an attribute. Will return null if an attribute is not set.
     */
    public String getAttribute(String key);

}
