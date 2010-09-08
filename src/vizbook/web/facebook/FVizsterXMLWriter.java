package vizbook.web.facebook;

import java.io.PrintWriter;
import java.util.*;

import org.json.*;
import com.google.code.facebookapi.*;

public class FVizsterXMLWriter {
	
	private StringBuilder out = new StringBuilder(), nodeRegion = new StringBuilder(), edgeRegion = new StringBuilder(); // get rid of this
	private int V = 0, E = 0;
	
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
	
	public FVizsterXMLWriter(FacebookJsonRestClient client, PrintWriter logger) {
		try {
			JSONArray friends = client.friends_get();
			ArrayList<Long> friendIds = new ArrayList<Long>();
			friendIds.add(client.users_getLoggedInUser());
			V = friends.length() + 1;
			for(int i = 1; i < V; i++) {
				friendIds.add(friends.getLong(i-1));
			}
			
			JSONArray results = client.users_getInfo(friendIds, fields.keySet());			
			for(int i = 0; i < V; i++) {
				addNode(results.getJSONObject(i));		
			
				long friendId = friendIds.get(i);
				
				ArrayList<Long> fakeList = new ArrayList<Long>();
				for(int j = i; j < V; j++)
					fakeList.add(friendId);
							
				JSONArray areFriends = (JSONArray)client.friends_areFriends(fakeList, friendIds.subList(i, V));
				for(int j = 0; j < areFriends.length(); j++) {
					JSONObject areFriend = areFriends.getJSONObject(j);
					if(Boolean.parseBoolean(areFriend.getString("are_friends")))
						addEdge(areFriend.getLong("uid1"), areFriend.getLong("uid2"));
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace(logger);
		} finally {
			out.append("<graph directed=\"0\">");
			out.append("\n\t<!-- nodes -->");
			out.append(nodeRegion);
			out.append("\n\t<!-- edges -->");
			out.append(edgeRegion);
			out.append("\n</graph>");
			
			logger.println("Got " + V +  " friends: ");
			logger.println();
			logger.println(out);
			logger.println();
			logger.println("Got " + E +  " edges"); // put these as comments in the graph file
			logger.println();
		}
	}
	
	private void addNode(JSONObject u) throws JSONException {		
		StringBuilder node = new StringBuilder("\n\t<node id=\"" + u.getLong(ProfileField.UID.fieldName()) + "\">");		  
		for(ProfileField pf : fields.keySet()) {
			String value = u.getString(pf.fieldName());
			if(value != null && value.length() > 0 && !value.equalsIgnoreCase("null"))
				node.append(String.format("\n\t\t<att name=\"%s\" value=\"%s\"/>", fields.get(pf), value));
		}		    
		node.append("\n\t</node>");
		nodeRegion.append(node);
	}
	
	private void addEdge(long id1, long id2) {
		E++;
		edgeRegion.append(String.format("\n\t<edge source=\"%d\" target=\"%d\"></edge>", id1, id2));	
	}
}