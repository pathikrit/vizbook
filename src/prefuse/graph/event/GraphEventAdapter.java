package prefuse.graph.event;

import prefuse.graph.Edge;
import prefuse.graph.Graph;
import prefuse.graph.Node;

/**
 * Adapter class to simplify creating GraphEventListener instances
 * 
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class GraphEventAdapter implements GraphEventListener {

	/**
	 * @see prefuse.graph.event.GraphEventListener#nodeAdded(prefuse.graph.Graph, prefuse.graph.Node)
	 */
	public void nodeAdded(Graph g, Node n) {} //

	/**
	 * @see prefuse.graph.event.GraphEventListener#nodeRemoved(prefuse.graph.Graph, prefuse.graph.Node)
	 */
	public void nodeRemoved(Graph g, Node n) {} //

	/**
	 * @see prefuse.graph.event.GraphEventListener#nodeReplaced(prefuse.graph.Graph, prefuse.graph.Node, prefuse.graph.Node)
	 */
	public void nodeReplaced(Graph g, Node o, Node n) {} //

	/**
	 * @see prefuse.graph.event.GraphEventListener#edgeAdded(prefuse.graph.Graph, prefuse.graph.Edge)
	 */
	public void edgeAdded(Graph g, Edge e) {} //

	/**
	 * @see prefuse.graph.event.GraphEventListener#edgeRemoved(prefuse.graph.Graph, prefuse.graph.Edge)
	 */
	public void edgeRemoved(Graph g, Edge e) {} //
    
	/**
	 * @see prefuse.graph.event.GraphEventListener#edgeReplaced(prefuse.graph.Graph, prefuse.graph.Edge, prefuse.graph.Edge)
	 */
	public void edgeReplaced(Graph g, Edge o, Edge n) {} //

} // end of class GraphEventAdapter
