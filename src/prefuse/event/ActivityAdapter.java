package prefuse.event;

import prefuse.activity.Activity;

/**
 * Adapter class for ActivityListeners. Provides empty implementations of
 * ActivityListener routines.
 * 
 * Feb 9, 2004 - jheer - Created class
 *
 * @version 1.0
 * @author <a href="http://jheer.org">Jeffrey Heer</a> prefuse(AT)jheer.org
 */
public class ActivityAdapter implements ActivityListener {

    /**
     * @see prefuse.event.ActivityListener#activityScheduled(prefuse.activity.Activity)
     */
    public void activityScheduled(Activity a) {
    } //

    /**
     * @see prefuse.event.ActivityListener#activityStarted(prefuse.activity.Activity)
     */
    public void activityStarted(Activity a) {
    } //

    /**
     * @see prefuse.event.ActivityListener#activityStepped(prefuse.activity.Activity)
     */
    public void activityStepped(Activity a) {
    } //

    /**
     * @see prefuse.event.ActivityListener#activityFinished(prefuse.activity.Activity)
     */
    public void activityFinished(Activity a) {
    } //

    /**
     * @see prefuse.event.ActivityListener#activityCancelled(prefuse.activity.Activity)
     */
    public void activityCancelled(Activity a) {
    } //

} // end of class ActivityAdapter
