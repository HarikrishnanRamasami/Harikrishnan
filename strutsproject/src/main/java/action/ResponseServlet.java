package action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ServiceRequest;



public class ResponseServlet {
	
	public void process(HttpServletRequest request, HttpServletResponse response) {
		
		try {
		
		String path=request.getServletContext().getAttribute("path").toString();
		
		String action=request.getParameter("action");
		
		Properties p=new Properties();
		
		p.load(new FileInputStream(path));
		
		String a=p.getProperty(action);
		
		ServiceRequest la=(ServiceRequest)Class.forName(a).getConstructor().newInstance();
		String b=la.executeService(request, response);		
		String c=p.getProperty(b);
		
		RequestDispatcher rd=request.getRequestDispatcher(c);
		
		rd.forward(request, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}