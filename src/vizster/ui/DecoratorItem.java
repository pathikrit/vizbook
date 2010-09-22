package vizster.ui;

import prefuse.ItemRegistry;
import prefuse.VisualItem;
import prefuse.graph.Entity;

/**
 * DecoratorItem
 *  
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class DecoratorItem extends VisualItem {

    private VisualItem m_decorated;

    public void init(ItemRegistry registry, String itemClass, Entity entity) {
        super.init(registry,itemClass,entity);
        m_decorated = registry.getItem(ItemRegistry.DEFAULT_NODE_CLASS, entity, false, false);
    } //
    
    public VisualItem getDecorated() {
        return m_decorated;
    } //
    
    public void setDecorated(VisualItem decorated) {
        this.m_decorated = decorated;
    } //
    
} // end of class DecoratorItem
