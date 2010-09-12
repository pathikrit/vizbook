package vizbook.web.facebook;

import java.util.*;
import org.json.*;
import com.google.code.facebookapi.*;

public class VizsterXMLWriter extends FacebookDataImportTask {
	
	// In debug mode fetches only up to 10 vertices
	private boolean DEBUG = false;
	
	public VizsterXMLWriter(FacebookJsonRestClient client, String name, String extension) {
		super(client, name, extension);		
	}

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
	    put(ProfileField.STATUS, "membersince"); 
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
			if(DEBUG) V = Math.min(V, 10);
			for(int i = 1; i < V; i++) {
				friendIds.add(friends.getLong(i-1));
			}
			log("Got " + V + " friends (including self): ");
			
			write("<graph directed=\"0\">");
			
			JSONArray results = client.users_getInfo(friendIds, fields.keySet());
			
			for(int i = 0; i < V; i++) {
				JSONObject u = results.getJSONObject(i);		
				String name = u.getString(fields.get(ProfileField.NAME));
				log(i + ". Processing " + name);
				
				//TODO: Implement FacebookException skip here				
				write("\t<node id=\"" + u.getLong(ProfileField.UID.fieldName()) + "\">");
				for(ProfileField pf : fields.keySet()) {
					String value = u.getString(pf.fieldName());
					if(value != null && value.length() > 0 && !value.equalsIgnoreCase("null"))
						write(String.format("\t\t<att name=\"%s\" value=\"%s\"/>", fields.get(pf), value));
				}		    
				write("\t</node>");				
			}
			
			for(int i = 0; i < V; i++) {
				long friendId = friendIds.get(i);				
				
				ArrayList<Long> fakeList = new ArrayList<Long>();
				for(int j = i; j < V; j++)
					fakeList.add(friendId);
							
				JSONArray areFriends = (JSONArray)client.friends_areFriends(fakeList, friendIds.subList(i, V));
				for(int j = 0; j < areFriends.length(); j++) {
					JSONObject areFriend = areFriends.getJSONObject(j);
					if(Boolean.parseBoolean(areFriend.getString("are_friends"))) {
						long id1 = areFriend.getLong("uid1"), id2 = areFriend.getLong("uid2");
						E++;
						write(String.format("\t<edge source=\"%d\" target=\"%d\"></edge>", id1, id2));
					}
				}
			}
			
			write("</graph>");			
			log("Got " + E +  " edges");
			
		} catch(Exception e) {
			logError(e.toString());
		}		
	}
}