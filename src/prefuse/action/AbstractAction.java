package prefuse.action;

import prefuse.ItemRegistry;

/**
 * Abstract implementation of the Action interface. Developers can subclass
 * this class and implement the <code>run</code> method to create their
 * own custom Actions.
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public abstract class AbstractAction implements Action {
    
    protected boolean  m_enabled;
    
    /**
     * Default constructor.
     */
    public AbstractAction() {
        m_enabled = true;
    } //

    /**
     * @see prefuse.action.Action#run(prefuse.ItemRegistry, double)
     */
    public abstract void run(ItemRegistry registry, double frac);

    /**
     * @see prefuse.action.Action#isEnabled()
     */
    public boolean isEnabled() {
        return m_enabled;
    } //
    
    /**
     * @see prefuse.action.Action#setEnabled(boolean)
     */
    public void setEnabled(boolean s) {
        m_enabled = s;
    } //

} // end of class Action
