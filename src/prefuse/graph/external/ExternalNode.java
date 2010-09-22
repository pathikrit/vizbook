package prefuse.graph.external;

import java.util.Iterator;

import prefuse.graph.DefaultNode;
import prefuse.graph.Edge;
import prefuse.graph.Node;

/**
 * Represents a node in a graph that pulls data from an external source,
 * such as a database or file system.
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class ExternalNode extends DefaultNode implements ExternalEntity {
    
    protected GraphLoader  m_loader;

    protected boolean m_loaded = false;
    protected boolean m_loadStarted = false;
    
    protected void checkLoadedStatus() {
        touch();
        if ( !m_loadStarted ) {
            m_loadStarted = true;
            m_loader.loadNeighbors(this);
        }
    } //
    
    public void setLoader(GraphLoader gl) {
        m_loader = gl;
    } //
    
    void setNeighborsLoaded(boolean s) {
        m_loaded = s;
        m_loadStarted = s;
    } //

    public boolean isNeighborsLoaded() {
        return m_loaded;
    } //
    
    public void touch() {
        m_loader.touch(this);
    } //
    
    public void unload() {
        Iterator iter = m_edges.iterator();
        while ( iter.hasNext() ) {
            Edge e = (Edge)iter.next();
            Node n = e.getAdjacentNode(this);
            n.removeEdge(e);
            if ( n instanceof ExternalNode ) {
                ((ExternalNode)n).setNeighborsLoaded(false);
            } else if ( n instanceof ExternalTreeNode ) {
                ((ExternalTreeNode)n).setParentLoaded(false);
                ((ExternalTreeNode)n).setChildrenLoaded(false);
            }
        }
        m_edges.clear();
    } //
    
    // ========================================================================
    // == PROXIED NODE METHODS ================================================

    /**
     * @see prefuse.graph.Node#addEdge(prefuse.graph.Edge)
     */
    public boolean addEdge(Edge e) {
        touch();
        return super.addEdge(e);
    } //
    
    /**
     * @see prefuse.graph.Node#addEdge(int, prefuse.graph.Edge)
     */
    public boolean addEdge(int i, Edge e) {
        touch();
        return super.addEdge(i,e);
    } //
    
    /**
     * @see prefuse.graph.Node#getEdge(int)
     */
    public Edge getEdge(int i) {
        checkLoadedStatus();
        return super.getEdge(i);
    } //
    
    /**
     * @see prefuse.graph.Node#getEdge(prefuse.graph.Node)
     */
    public Edge getEdge(Node n) {
        checkLoadedStatus();
        return super.getEdge(n);
    } //

    /**
     * @see prefuse.graph.Node#getEdgeCount()
     */
    public int getEdgeCount() {
        touch();
        return super.getEdgeCount();
    } //
    
    /**
     * @see prefuse.graph.Node#getEdges()
     */
    public Iterator getEdges() {
        checkLoadedStatus();
        return super.getEdges();
    } //
    
    /**
     * @see prefuse.graph.Node#getIndex(prefuse.graph.Edge)
     */
    public int getIndex(Edge e) {
        touch();
        return super.getIndex(e);
    } //

    /**
     * @see prefuse.graph.Node#getIndex(prefuse.graph.Node)
     */
    public int getIndex(Node n) {
        touch();
        return super.getIndex(n);
    } //
    
    /**
     * @see prefuse.graph.Node#getNeighbor(int)
     */
    public Node getNeighbor(int i) {
        checkLoadedStatus();
        return super.getNeighbor(i);
    } //

    /**
     * @see prefuse.graph.Node#getNeighbors()
     */
    public Iterator getNeighbors() {
        checkLoadedStatus();
        return super.getNeighbors();
    } //
    
    /**
     * @see prefuse.graph.Node#isIncidentEdge(prefuse.graph.Edge)
     */
    public boolean isIncidentEdge(Edge e) {
        touch();
        return super.isIncidentEdge(e);
    } //

    /**
     * @see prefuse.graph.Node#isNeighbor(prefuse.graph.Node)
     */
    public boolean isNeighbor(Node n) {
        touch();
        return super.isNeighbor(n);
    } //
    
    /**
     * @see prefuse.graph.Node#removeEdge(prefuse.graph.Edge)
     */
    public boolean removeEdge(Edge e) {
        touch();
        return super.removeEdge(e);
    } //
    
    /**
     * @see prefuse.graph.Node#removeEdge(int)
     */
    public Edge removeEdge(int i) {
        touch();
        return super.removeEdge(i);
    } //

    /**
     * @see prefuse.graph.Node#removeNeighbor(int)
     */
    public Node removeNeighbor(int i) {
        touch();
        return super.removeNeighbor(i);
    } //

    /**
     * @see prefuse.graph.Node#removeNeighbor(prefuse.graph.Node)
     */
    public boolean removeNeighbor(Node n) {
        touch();
        return super.removeNeighbor(n);
    } //
    
} // end of class ExternalNode
