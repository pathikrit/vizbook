package vizbook.web.facebook;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.google.code.facebookapi.FacebookJsonRestClient;

import vizbook.web.WebLoggingTask;

/**
 * Super class for all data import jobs from Facebook
 *
 */
public abstract class FacebookDataImportTask extends WebLoggingTask {

	protected FacebookJsonRestClient client;
	private Writer output;
	private String fileName;
	
	protected FacebookDataImportTask(FacebookJsonRestClient client, String name, String extension) {
		this.client = client;
		try {
			fileName = String.format("C:\\Users\\Wrick\\Documents\\%s-%d.%s", name, client.users_getLoggedInUser(), extension);			
			output = new PrintWriter(new File(fileName));
		} catch(Exception e) {
			logError("Could not create output file: " + e.getMessage());
		}	
	}
	
	protected void write(String line) throws IOException {
		if(output != null) output.write("\n" + line);
	}
	
	/**
	 * All data import tasks must implement this method
	 */
	protected abstract void fetchData();
	
	public void run() {
		fetchData();
		cleanUp();
	}
	
	private void cleanUp() {
		if(output != null) {
			try {
				output.flush();
				output.close();
				log("Output is ready at: " + fileName);
			} catch (IOException e) {
				logError("Could not close output file: " + e.getLocalizedMessage());
			}
		}
	}
}
