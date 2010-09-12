package vizbook.web.facebook;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;

@SuppressWarnings("serial")
@WebServlet(
		urlPatterns = {"/FacebookMain"}, 
		initParams = { 
				@WebInitParam(name = "API_KEY", value = "e19760c3ea4e06f07d417f30a59a81da", description = "API Key"), 
				@WebInitParam(name = "SECRET_KEY", value = "f9865fdf21a2234964841aeaaa561a8d", description = "Application Secret")
		})
public class FacebookServlet extends HttpServlet {	
	
	private FacebookDataImportTask dataImportTask = null;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       HttpSession session = request.getSession();
       
       String apiKey = getServletConfig().getInitParameter("API_KEY"), 
       	      secretKey = getServletConfig().getInitParameter("SECRET_KEY"),
       	      sessionKey = (String)session.getAttribute("restSearchAppSession"), 
       	      authToken = request.getParameter("auth_token");
   
       FacebookJsonRestClient client = null;
       if (sessionKey != null) {    	   
    	   client = new FacebookJsonRestClient(apiKey, secretKey, sessionKey);    	   
       } else if (authToken != null) {    	   
    	   client = new FacebookJsonRestClient(apiKey, secretKey);         
           try {
        	   sessionKey = client.auth_getSession(authToken);
           } catch (FacebookException e) {			
        	   e.printStackTrace();
           }
           session.setAttribute("restSearchAppSession", sessionKey);         
       } else {    	   
    	   response.sendRedirect("http://www.facebook.com/login.php?api_key="+apiKey);
    	   return;
       }
       
       dataImportTask = new VizsterXMLWriter(client, "vizster", "xml");
       request.getRequestDispatcher("FacebookDataViewer.jsp").forward(request, response);
       dataImportTask.start();
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(dataImportTask != null)
    		response.getWriter().write(dataImportTask.getLog());
    	//TODO: If done send to a page containing the applet
	}
}