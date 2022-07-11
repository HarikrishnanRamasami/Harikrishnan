package control;

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Action;

public class RequestProcessor 
{
	public void process(HttpServletRequest request,HttpServletResponse response)
	{
		try {
				String path=request.getServletContext().getAttribute("path").toString();
				String action=request.getParameter("action");
				Properties prop=new Properties();
				prop.load(new FileInputStream(path));
				String actionclass=prop.getProperty("action");
				Action actionObj=(Action)Class.forName(actionclass).getConstructor().newInstance();
				String actionresult=actionObj.execute(request, response);
				String nextPage=prop.getProperty(actionresult);
				RequestDispatcher rd=request.getRequestDispatcher(nextPage);
				rd.forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
