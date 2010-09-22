package vizster.action.linkage;

import java.util.Iterator;

import vizster.Vizster;
import prefuse.FocusManager;
import prefuse.ItemRegistry;
import prefuse.NodeItem;
import prefuse.action.assignment.Layout;
import prefuse.focus.FocusSet;
import prefuse.graph.Node;

/**
 * ReleaseFixedAction
 *  
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class ReleaseFixedAction extends Layout {

    /**
     * @see prefuse.action.Action#run(prefuse.ItemRegistry, double)
     */
    public void run(ItemRegistry registry, double frac) {
        FocusManager fman = registry.getFocusManager();
        FocusSet click = fman.getFocusSet(Vizster.CLICK_KEY);
        //FocusSet mouse = fman.getFocusSet(Vizster.MOUSE_KEY);
		Iterator<?> nodeIter = click.iterator();
		while ( nodeIter.hasNext() ) {
		    Node n = (Node)nodeIter.next();
		    NodeItem nitem = registry.getNodeItem(n);
		    nitem.setFixed(false);
		    double x = nitem.getStartLocation().getX();
		    double y = nitem.getStartLocation().getY();
		    setLocation(nitem, null, x, y);
		}
    } //

} // end of class ReleaseFixedAction
