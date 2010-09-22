package vizster.action;

import java.util.Iterator;

import vizster.Vizster;
import prefuse.FocusManager;
import prefuse.ItemRegistry;
import prefuse.NodeItem;
import prefuse.VisualItem;
import prefuse.action.filter.Filter;
import prefuse.focus.FocusSet;
import prefuse.graph.Node;

/**
 * AuraFilter
 *  
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class AuraFilter extends Filter {

    public AuraFilter() {
        super(Vizster.AURA_CLASS, true);
    } //
    
    /**
     * @see prefuse.action.Action#run(prefuse.ItemRegistry, double)
     */
    public void run(ItemRegistry registry, double frac) {
        FocusManager fman = registry.getFocusManager();
        FocusSet set = fman.getFocusSet(Vizster.SEARCH_KEY);
        
        synchronized ( set ) {
	        Iterator<?> iter = set.iterator();
	        while ( iter.hasNext() ) {
	            Node n = (Node) iter.next();
	            NodeItem nitem = registry.getNodeItem(n);
	            if ( nitem != null ) {
	                VisualItem item = registry.getItem(Vizster.AURA_CLASS, n, true, true);
	                item.setInteractive(false);
	            }
	        }
        }
        
        // garbage collect
        super.run(registry, frac);
    } //
    
} // end of class AuraFilter
