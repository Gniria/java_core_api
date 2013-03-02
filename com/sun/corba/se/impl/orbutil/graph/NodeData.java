/*
 * @(#)NodeData.java	1.4 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.corba.se.impl.orbutil.graph ;

/** Data about a node in a graph. 
 */
public class NodeData 
{
    private boolean visited ;
    private boolean root ;

    public NodeData()
    {
	clear() ;
    }

    public void clear()
    {
	this.visited = false ;
	this.root = true ;
    }

    /** Return whether this node has been visited in a traversal.
     * Note that we only support a single traversal at a time.
     */
    boolean isVisited() 
    {
	return visited ;
    }

    void visited()
    {
	visited = true ;
    }

    /** Return whether this node is a root.
     */
    boolean isRoot() 
    {
	return root ;
    }

    void notRoot()
    {
	root = false ;
    }
}
