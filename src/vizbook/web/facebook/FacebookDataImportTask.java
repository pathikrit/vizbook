package vizbook.web.facebook;

import com.google.code.facebookapi.FacebookJsonRestClient;

import vizbook.web.WebLoggingTask;

/**
 * Super class for all data import jobs from Facebook
 *
 */
public abstract class FacebookDataImportTask extends WebLoggingTask {

	protected FacebookJsonRestClient client;	
	
	public FacebookDataImportTask(FacebookJsonRestClient client) {
		this.client = client;		
	}
}
