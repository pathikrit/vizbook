package vizbook.web.demo;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vizbook.web.WebLoggingTask;

@SuppressWarnings("serial")
@WebServlet("/LogDemo")
public class LogDemoServlet extends HttpServlet {	
	
	private WebLoggingTask loggingTask = new RandomLogger();
       
    public LogDemoServlet() {
        super();        
        loggingTask.start();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);		
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Writer writer = response.getWriter();
    	if(loggingTask.isRunning())
    		writer.write(loggingTask.getLog());
    	else
    		writer.write("Done!");
	}
}
