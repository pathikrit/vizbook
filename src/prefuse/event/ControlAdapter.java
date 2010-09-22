package prefuse.event;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import prefuse.VisualItem;

/**
 * Adapter class for prefuse interface events. Subclasses can override the
 * desired methods to perform user interface event handling.
 * 
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class ControlAdapter implements ControlListener {

	/**
	 * @see prefuse.event.ControlListener#itemDragged(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemDragged(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemMoved(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemMoved(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemWheelMoved(prefuse.VisualItem, java.awt.event.MouseWheelEvent)
	 */
	public void itemWheelMoved(VisualItem gi, MouseWheelEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemClicked(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemClicked(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemPressed(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemPressed(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemReleased(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemReleased(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemEntered(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemEntered(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemExited(prefuse.VisualItem, java.awt.event.MouseEvent)
	 */
	public void itemExited(VisualItem gi, MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemKeyPressed(prefuse.VisualItem, java.awt.event.KeyEvent)
	 */
	public void itemKeyPressed(VisualItem item, KeyEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemKeyReleased(prefuse.VisualItem, java.awt.event.KeyEvent)
	 */
	public void itemKeyReleased(VisualItem item, KeyEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#itemKeyTyped(prefuse.VisualItem, java.awt.event.KeyEvent)
	 */
	public void itemKeyTyped(VisualItem item, KeyEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {	
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {		
	} //

	/**
	 * @see prefuse.event.ControlListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {	
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
	} //

	/**
	 * @see prefuse.event.ControlListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	} //

} // end of class ControlAdapter
