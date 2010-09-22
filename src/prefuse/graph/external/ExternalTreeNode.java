package prefuse.graph.external;

import java.util.Iterator;

import prefuse.graph.DefaultTreeNode;
import prefuse.graph.Edge;
import prefuse.graph.Node;
import prefuse.graph.TreeNode;

/**
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class ExternalTreeNode extends DefaultTreeNode implements ExternalEntity {

    protected static final int LOAD_CHILDREN = 1;
    protected static final int LOAD_PARENT   = 2;
    protected static final int LOAD_ALL      = LOAD_CHILDREN | LOAD_PARENT;
    
    protected GraphLoader  m_loader;

    protected boolean m_ploaded = false;
    protected boolean m_ploadStarted = false;
    protected boolean m_loaded = false;
    protected boolean m_loadStarted = false;
    
    protected void checkLoadedStatus(int type) {
        touch();
        if ( (type & LOAD_CHILDREN) > 0 && !m_loadStarted ) {
            m_loadStarted = true;
            m_loader.loadChildren(this);
        }
        if ( (type & LOAD_PARENT) > 0 && !m_ploadStarted ) {
            m_ploadStarted = true;
            m_loader.loadParent(this);
        }
    } //
    
    public void setLoader(GraphLoader gl) {
        m_loader = gl;
    } //
    
    void setChildrenLoaded(boolean s) {
        m_loaded = s;
        m_loadStarted = s;
    } //
    
    void setParentLoaded(boolean s) {
        m_ploaded = s;
        m_ploadStarted = s;
    }

    public boolean isParentLoaded() {
        return m_ploaded;
    } //
    
    public boolean isChildrenLoaded() {
        return m_loaded;
    } //
    
    public void touch() {
        m_loader.touch(this);
    } //
    
    public void unload() {
        Iterator iter;
        if ( m_children != null ) {
            iter = m_children.iterator();
            while ( iter.hasNext() ) {
                Edge e = (Edge)iter.next();
                TreeNode n = (TreeNode)e.getAdjacentNode(this);
                n.removeAsChild(this);
                if ( n instanceof ExternalTreeNode )
                    ((ExternalTreeNode)n).setParentLoaded(false);
            }
            m_children.clear();
        }
        
        m_parent.removeChild(this);
        if ( m_parent instanceof ExternalTreeNode )
            ((ExternalTreeNode)m_parent).setChildrenLoaded(false);
        m_parent = null;
        m_parentEdge = null;
        
        iter = m_edges.iterator();
        while ( iter.hasNext() ) {
            Edge e = (Edge)iter.next();
            Node n = e.getAdjacentNode(this);
            n.removeEdge(e);
        }
        m_edges.clear();
    } //
    
    // ========================================================================
    // == PROXIED TREE NODE METHODS ===========================================
    
    /**
     * @see prefuse.graph.TreeNode#addChild(prefuse.graph.Edge)
     */
    public boolean addChild(Edge e) {
        touch();
        return super.addChild(e);
    } //

    /**
     * @see prefuse.graph.TreeNode#addChild(int, prefuse.graph.Edge)
     */
    public boolean addChild(int idx, Edge e) {
        touch();
        return super.addChild(idx, e);
    } //

    /**
     * @see prefuse.graph.TreeNode#getChild(int)
     */
    public TreeNode getChild(int idx) {
        checkLoadedStatus(LOAD_CHILDREN);
        return super.getChild(idx);
    } //

    /**
     * @see prefuse.graph.TreeNode#getChildCount()
     */
    public int getChildCount() {
        touch();
        return super.getChildCount();
    } //

    /**
     * @see prefuse.graph.TreeNode#getChildEdge(int)
     */
    public Edge getChildEdge(int i) {
        checkLoadedStatus(LOAD_CHILDREN);
        return super.getChildEdge(i);
    } //

    /**
     * @see prefuse.graph.TreeNode#getChildEdges()
     */
    public Iterator getChildEdges() {
        checkLoadedStatus(LOAD_CHILDREN);
        return super.getChildEdges();
    } //

    /**
     * @see prefuse.graph.TreeNode#getChildIndex(prefuse.graph.Edge)
     */
    public int getChildIndex(Edge e) {
        touch();
        return super.getChildIndex(e);
    } //

    /**
     * @see prefuse.graph.TreeNode#getChildIndex(prefuse.graph.TreeNode)
     */
    public int getChildIndex(TreeNode c) {
        touch();
        return super.getChildIndex(c);
    } //

    /**
     * @see prefuse.graph.TreeNode#getChildren()
     */
    public Iterator getChildren() {
        checkLoadedStatus(LOAD_CHILDREN);
        return super.getChildren();
    } //

    /**
     * @see prefuse.graph.TreeNode#getNextSibling()
     */
    public TreeNode getNextSibling() {
        checkLoadedStatus(LOAD_PARENT);
        return super.getNextSibling();
    } //

    /**
     * @see prefuse.graph.TreeNode#getDescendantCount()
     */
    public int getDescendantCount() {
        touch();
        return super.getDescendantCount();
    } //

    /**
     * @see prefuse.graph.TreeNode#getParent()
     */
    public TreeNode getParent() {
        checkLoadedStatus(LOAD_PARENT);
        return super.getParent();
    } //

    /**
     * @see prefuse.graph.TreeNode#getParentEdge()
     */
    public Edge getParentEdge() {
        checkLoadedStatus(LOAD_PARENT);
        return super.getParentEdge();
    } //

    /**
     * @see prefuse.graph.TreeNode#getPreviousSibling()
     */
    public TreeNode getPreviousSibling() {
        checkLoadedStatus(LOAD_PARENT);
        return super.getPreviousSibling();
    } //

    /**
     * @see prefuse.graph.TreeNode#isChild(prefuse.graph.TreeNode)
     */
    public boolean isChild(TreeNode c) {
        touch();
        return super.isChild(c);
    } //

    /**
     * @see prefuse.graph.TreeNode#isChildEdge(prefuse.graph.Edge)
     */
    public boolean isChildEdge(Edge e) {
        touch();
        return super.isChildEdge(e);
    } //

    /**
     * @see prefuse.graph.TreeNode#isDescendant(prefuse.graph.TreeNode)
     */
    public boolean isDescendant(TreeNode n) {
        touch();
        return super.isDescendant(n);
    } //

    /**
     * @see prefuse.graph.TreeNode#isSibling(prefuse.graph.TreeNode)
     */
    public boolean isSibling(TreeNode n) {
        checkLoadedStatus(LOAD_PARENT);
        return super.isSibling(n);
    } //

    /**
     * @see prefuse.graph.TreeNode#removeAllAsChildren()
     */
    public void removeAllAsChildren() {
        touch();
        super.removeAllAsChildren();
    } //

    /**
     * @see prefuse.graph.TreeNode#removeAllChildren()
     */
    public void removeAllChildren() {
        touch();
        super.removeAllChildren();
    } //

    /**
     * @see prefuse.graph.TreeNode#removeAsChild(int)
     */
    public TreeNode removeAsChild(int idx) {
        touch();
        return super.removeAsChild(idx);
    } //

    /**
     * @see prefuse.graph.TreeNode#removeAsChild(prefuse.graph.TreeNode)
     */
    public boolean removeAsChild(TreeNode n) {
        touch();
        return super.removeAsChild(n);
    } //

    /**
     * @see prefuse.graph.TreeNode#removeChild(int)
     */
    public TreeNode removeChild(int idx) {
        touch();
        return super.removeChild(idx);
    } //

    /**
     * @see prefuse.graph.TreeNode#removeChild(prefuse.graph.TreeNode)
     */
    public boolean removeChild(TreeNode n) {
        touch();
        return super.removeChild(n);
    } //

    /**
     * @see prefuse.graph.TreeNode#removeChildEdge(prefuse.graph.Edge)
     */
    public boolean removeChildEdge(Edge e) {
        touch();
        return super.removeChildEdge(e);
    } //

    /**
     * @see prefuse.graph.TreeNode#removeChildEdge(int)
     */
    public Edge removeChildEdge(int idx) {
        touch();
        return super.removeChildEdge(idx);
    } //

    /**
     * @see prefuse.graph.TreeNode#setAsChild(int, prefuse.graph.TreeNode)
     */
    public boolean setAsChild(int idx, TreeNode c) {
        touch();
        return super.setAsChild(idx,c);
    } //

    /**
     * @see prefuse.graph.TreeNode#setAsChild(prefuse.graph.TreeNode)
     */
    public boolean setAsChild(TreeNode c) {
        touch();
        return super.setAsChild(c);
    } //

    /**
     * @see prefuse.graph.TreeNode#setDescendantCount(int)
     */
    public void setDescendantCount(int count) {
        touch();
        super.setDescendantCount(count);
    } //

    /**
     * @see prefuse.graph.TreeNode#setParentEdge(prefuse.graph.Edge)
     */
    public void setParentEdge(Edge e) {
        touch();
        super.setParentEdge(e);
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
        checkLoadedStatus(LOAD_ALL);
        return super.getEdge(i);
    } //
    
    /**
     * @see prefuse.graph.Node#getEdge(prefuse.graph.Node)
     */
    public Edge getEdge(Node n) {
        checkLoadedStatus(LOAD_ALL);
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
        checkLoadedStatus(LOAD_ALL);
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
        checkLoadedStatus(LOAD_ALL);
        return super.getNeighbor(i);
    } //

    /**
     * @see prefuse.graph.Node#getNeighbors()
     */
    public Iterator getNeighbors() {
        checkLoadedStatus(LOAD_ALL);
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
    
} // end of class ExternalTreeNode
