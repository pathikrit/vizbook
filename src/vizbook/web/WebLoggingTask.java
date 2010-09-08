package vizbook.web;

import vizbook.web.demo.LogDemoServlet;

/**
 * Every thread spawned by a servlet that needs to output should implement the run method of this class. 
 * See {@link LogDemoServlet}
 * 
 */
public abstract class WebLoggingTask extends Thread {
	private StringBuffer log = new StringBuffer();
	
	public synchronized String getLog() {
		String ret = log.toString();
		log = log.delete(0, ret.length());
		return ret;
	}
	
	public synchronized void logError(String s) {
		log(String.format("<b>%s</b>", s));
	}
	
	public synchronized void log(String s) {
		log.append("<br/>").append(s);
	}
	
	public abstract void run();
}
