package vizbook.web.facebook;

import java.text.Normalizer;
import java.util.*;

import org.json.*;
import com.google.code.facebookapi.*;

public class VizsterXMLWriter extends FacebookDataImportTask {
	
	private int DEBUG_LENGTH = -1;	// Fetch only upto DEBUG_LENGTH vertices, set to -1 otherwise
	
	public VizsterXMLWriter(FacebookJsonRestClient client, String name, String extension) {
		super(client, name, extension);		
	}

	//TODO: retrieve more information
	@SuppressWarnings("serial")
	private static HashMap<ProfileField, String> fields = new HashMap<ProfileField, String>() {{
	    put(ProfileField.UID, "uid");
	    put(ProfileField.NAME, "name");
	    put(ProfileField.LOCALE, "location");
	    put(ProfileField.BIRTHDAY, "age");
	    put(ProfileField.SEX, "gender");
	    put(ProfileField.RELATIONSHIP_STATUS, "status");
	    put(ProfileField.MEETING_SEX, "interested_in");
	    put(ProfileField.MEETING_FOR, "preference");
	    put(ProfileField.LOCALE, "location");
//	    put(ProfileField.HOMETOWN_LOCATION, "hometown");
//	    put(ProfileField.WORK_HISTORY, "occupation");
	    put(ProfileField.INTERESTS, "interests");
	    put(ProfileField.MUSIC, "music");
	    put(ProfileField.BOOKS, "books");
	    put(ProfileField.TV, "tvshows");
	    put(ProfileField.MOVIES, "movies");
//	    put(ProfileField.STATUS, "membersince"); 
	    put(ProfileField.ONLINE_PRESENCE, "lastlogin");
	    put(ProfileField.PROFILE_UPDATE_TIME, "lastmod");
	    put(ProfileField.ABOUT_ME, "about");
	    put(ProfileField.SIGNIFICANT_OTHER_ID, "want_to_meet");
	    put(ProfileField.PIC_SMALL, "photourl");
	}};	

	@Override
	public void fetchData() {
		//TODO: Enforce code style		
		int V = 0, E = 0;
		try {
			JSONArray friends = client.friends_get();
			ArrayList<Long> friendIds = new ArrayList<Long>();
			friendIds.add(client.users_getLoggedInUser());
			V = friends.length() + 1;	
			if(DEBUG_LENGTH > 0) V = Math.min(V, DEBUG_LENGTH);
			for(int i = 1; i < V; i++) {
				friendIds.add(friends.getLong(i-1));
			}
			
			write("<graph directed=\"0\">");
			
			JSONArray results = client.users_getInfo(friendIds, fields.keySet());
			
			//TODO: More informative logError(String msg, Error e)
			for(int i = 0; i < V; i++) {				
				try {
					StringBuilder node = new StringBuilder();
					JSONObject u = results.getJSONObject(i);
					String name = u.getString(fields.get(ProfileField.NAME));					
									
					node.append("\t<node id=\"" + u.getLong(ProfileField.UID.fieldName()) + "\">");
					for(ProfileField pf : fields.keySet()) {
						String value = u.getString(pf.fieldName());
						if(value != null && value.length() > 0 && !value.equalsIgnoreCase("null"))
							node.append(String.format("\n\t\t<att name=\"%s\" value=\"%s\"/>", fields.get(pf), clean(value)));
					}		    
					node.append("\n\t</node>");
					
					write(node.toString());
					log(i + ". Processed attributes of " + name);					
				} catch(JSONException je) {
					logError(je.getLocalizedMessage());
				}
			}
			
			//TODO: combine node and edge into single loop; write to tempBuffer
			log("Finished writing node attributes of " + V + " friends (including self): ");
			log("Starting edge analysis...");
			
			for(int i = 0; i < V; i++) {
				try {
					JSONObject u = results.getJSONObject(i);		
					String name = u.getString(fields.get(ProfileField.NAME));
					
					long friendId = friendIds.get(i);				
					
					ArrayList<Long> fakeList = new ArrayList<Long>();
					for(int j = i; j < V; j++)
						fakeList.add(friendId);
								
					JSONArray areFriends = (JSONArray)client.friends_areFriends(fakeList, friendIds.subList(i, V));
					int common = 0;
					for(int j = 0; j < areFriends.length(); j++) {
						try {
							//TODO: Add support for nFriends
							JSONObject areFriend = areFriends.getJSONObject(j);
							if(Boolean.parseBoolean(areFriend.getString("are_friends"))) {
								long id1 = areFriend.getLong("uid1"), id2 = areFriend.getLong("uid2");
								E++;
								common++;
								write(String.format("\t<edge source=\"%d\" target=\"%d\"></edge>", id1, id2));
							}
						} catch(JSONException je) {
							logError(je.getLocalizedMessage());
						}
					}
					log(String.format("%d. Processed friends of %s (%d+ common friends)", i, name, common));
				} catch(JSONException je) {
					logError(je.getLocalizedMessage());
				}
			}
			
			write("</graph>");			
			log("Got " + E +  " edges");
			
		} catch(Exception e) {
			logError(e.toString());
		}		
	}
	
	//TODO: make this a regex
	private static String clean(String s) {
		s = s.replace("\n", " ").replaceAll("\r", " "); // Remove line breaks
		s = s.replaceAll("\\s+", " ").trim(); //Normalize white space		
		s = s.replaceAll("\\p{Punct}+", ",");
		s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); //Remove diacriticals
		//s = s.replaceAll("^\\p{Print}+", "?"); //Everything else becomes a question mark
		//s = StringEscape
		return s;
	}	
}