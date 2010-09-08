package vizbook.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vizbook.WebLoggingTask;

@WebServlet("/LogDemo")
public class LogDemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebLoggingTask loggingTask;
       
    public LogDemoServlet() {
        super();
        loggingTask = new RandomLogger();
        loggingTask.start();        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(loggingTask.getLog());		
	}
}
