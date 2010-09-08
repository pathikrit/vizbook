package vizbook.web.facebook;

import java.util.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.*;
import com.google.code.facebookapi.*;

public class VizsterXMLWriter extends FacebookDataImporter {	
	
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
	
	public VizsterXMLWriter(FacebookJsonRestClient client) {
		super(client);
	}
	
	@Override
	public void run() {
		//TODO: Enforce code style
		StringBuilder nodeRegion = new StringBuilder(), edgeRegion = new StringBuilder(); // get rid of this
		int V = 0, E = 0;
		try {
			JSONArray friends = client.friends_get();
			ArrayList<Long> friendIds = new ArrayList<Long>();
			friendIds.add(client.users_getLoggedInUser());
			V = friends.length() + 1;			
			for(int i = 1; i < V; i++) {
				friendIds.add(friends.getLong(i-1));
			}
			log("Got " + V + " friends (including self): ");
			
			JSONArray results = client.users_getInfo(friendIds, fields.keySet());			
			for(int i = 0; i < V; i++) {
				JSONObject u = results.getJSONObject(i);		
				String name = u.getString(fields.get(ProfileField.NAME));
				log(i + ". Processing " + name);
				
				StringBuilder node = new StringBuilder();
				node.append("&nbsp&nbsp");
				node.append(StringEscapeUtils.escapeHtml("<node id=\"" + u.getLong(ProfileField.UID.fieldName()) + "\">"));
				for(ProfileField pf : fields.keySet()) {
					String value = u.getString(pf.fieldName());
					if(value != null && value.length() > 0 && !value.equalsIgnoreCase("null"))
						node.append("<br/>&nbsp&nbsp&nbsp&nbsp").append(StringEscapeUtils.escapeHtml(String.format("<att name=\"%s\" value=\"%s\"/>", fields.get(pf), value)));
				}		    
				node.append("<br/>&nbsp&nbsp").append(StringEscapeUtils.escapeHtml("</node>"));
				nodeRegion.append(node);
			
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
						edgeRegion.append("<br/>&nbsp&nbsp").append(StringEscapeUtils.escapeHtml(String.format("<edge source=\"%d\" target=\"%d\"></edge>", id1, id2)));
					}
				}
			}
			
			log("<br/>Got " + E +  " edges");
			
		} catch(Exception e) {
			logError(e.toString());
		} finally {			
			log("-------------------");
			log(StringEscapeUtils.escapeHtml("<graph directed=\"0\">"));			
			log(nodeRegion.toString());			
			log(edgeRegion.toString());
			log(StringEscapeUtils.escapeHtml("</graph>"));
		}		
	}
}