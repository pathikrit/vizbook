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
	
	private final static String OUT_DIR = System.getProperty("user.home");
	
	protected FacebookDataImportTask(FacebookJsonRestClient client, String name, String extension) {
		this.client = client;
		// TODO: Examine output directory and ask to load data iff forced
		try {
			// TODO: Make it write to a project level directory
			fileName = String.format(OUT_DIR + "%s-%d-%d.%s", name, client.users_getLoggedInUser(), System.currentTimeMillis(), extension);			
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
	
	@Override
	public void task() {
		log("Starting data import... Please be patient and don't close this window.");
		fetchData();
		
		// cleanup
		if(output != null) {
			try {
				output.flush();
				output.close();
				//TODO: Write secret done message here
				log("Output is ready at: " + fileName);
			} catch (IOException e) {
				logError("Could not close output file: " + e.getLocalizedMessage());
			}
		}
	}
}
