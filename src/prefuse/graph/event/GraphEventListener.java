package prefuse.graph.event;

import java.util.EventListener;

import prefuse.graph.Edge;
import prefuse.graph.Graph;
import prefuse.graph.Node;

/**
 * A listener interface for monitoring changes to a graph structure.
 * 
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public interface GraphEventListener extends EventListener {

	public void nodeAdded(Graph g, Node n);
	public void nodeRemoved(Graph g, Node n);
	public void nodeReplaced(Graph g, Node o, Node n);
	
	public void edgeAdded(Graph g, Edge e);
	public void edgeRemoved(Graph g, Edge e);
	public void edgeReplaced(Graph g, Edge o, Edge n);
	
} // end of interface GraphEventListener
