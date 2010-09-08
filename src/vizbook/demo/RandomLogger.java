package vizbook.demo;

import vizbook.WebLoggingTask;

/**
 * A simple test logger that logs numbers from 0 to 99
 * Every odd number is logged as an error
 * 
 * @author rick
 */
public class RandomLogger extends WebLoggingTask {
	
	@Override
	public void run() {
		for(int i = 0; i < 100; i++) {
			if(i%2 == 0)
				log(""+i);
			else
				logError(""+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				logError(e.getMessage());
			}
		}		
	}
}
