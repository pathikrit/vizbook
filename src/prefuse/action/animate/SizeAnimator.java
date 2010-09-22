package prefuse.action.animate;

import java.util.Iterator;

import prefuse.VisualItem;
import prefuse.ItemRegistry;
import prefuse.action.AbstractAction;

/**
 * Linearly interpolates the size of a VisualItem.
 * 
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class SizeAnimator extends AbstractAction {

	public static final String ATTR_ANIM_FRAC = "animationFrac";

	/**
	 * @see prefuse.action.Action#run(prefuse.ItemRegistry, double)
	 */
	public void run(ItemRegistry registry, double frac) {
		double ss, es, s;		
		
		Iterator itemIter = registry.getItems();
		while ( itemIter.hasNext() ) {
			VisualItem item = (VisualItem)itemIter.next();
			ss = item.getStartSize();
			es = item.getEndSize();						
			s = ss + frac * (es - ss);						
			item.setSize(s);
		}		
	} //

} // end of class SizeAnimator
