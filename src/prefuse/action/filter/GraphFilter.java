package prefuse.action.filter;

import java.util.Iterator;

import prefuse.EdgeItem;
import prefuse.ItemRegistry;
import prefuse.NodeItem;
import prefuse.graph.DefaultGraph;
import prefuse.graph.Edge;
import prefuse.graph.Graph;
import prefuse.graph.Node;

/**
 * Filters a graph in it's entirety.
 * By default, garbage collection on node and edge items is performed.
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class GraphFilter extends Filter {

    public static final String[] ITEM_CLASSES = 
        {ItemRegistry.DEFAULT_NODE_CLASS, ItemRegistry.DEFAULT_EDGE_CLASS};
    
    // determines if filtered edges are visible by default
    protected boolean m_edgesVisible; 
    
    /**
     * Creates a new GraphFilter.
     */
    public GraphFilter() {
        this(true,true);
    } //
    
    public GraphFilter(boolean edgesVisible) {
        this(edgesVisible, true);
    } //
    
    public GraphFilter(boolean edgesVisible, boolean garbageCollect) {
        super(ITEM_CLASSES, garbageCollect);
        m_edgesVisible = edgesVisible;
    } //
    
    public void run(ItemRegistry registry, double frac) {
        Graph graph = registry.getGraph();
        // initialize filtered graph
        Graph fgraph = registry.getFilteredGraph();
        if ( fgraph instanceof DefaultGraph )
            ((DefaultGraph)fgraph).reinit(graph.isDirected());
        else
            fgraph = new DefaultGraph(graph.isDirected());
        
        // filter the nodes
        Iterator nodeIter = graph.getNodes();
        while ( nodeIter.hasNext() ) {
            NodeItem item = registry.getNodeItem((Node)nodeIter.next(), true, true);
            fgraph.addNode(item);
        }
        
        // process each node's edges
        nodeIter = fgraph.getNodes();
        while ( nodeIter.hasNext() ) {
            NodeItem item = (NodeItem)nodeIter.next();
            Node     node = (Node)item.getEntity();
            Iterator edgeIter = node.getEdges();
            while ( edgeIter.hasNext() ) {
                Edge edge = (Edge)edgeIter.next();
                Node n = edge.getAdjacentNode(node);
                // filter the edge
                EdgeItem eitem = registry.getEdgeItem(edge, true);
                fgraph.addEdge(eitem);
                if ( !m_edgesVisible ) eitem.setVisible(false);
            }
        }
        
        // update the registry's filtered graph
        registry.setFilteredGraph(fgraph);
        
        // optional garbage collection
        super.run(registry, frac);
    } //
    
} // end of class GraphFilter
