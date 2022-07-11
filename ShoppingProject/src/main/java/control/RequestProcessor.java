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
				String ac=prop.getProperty(action);
				Action ao=(Action)Class.forName(ac).getConstructor().newInstance();
				String as=ao.execute(request, response);
				String nextPage=prop.getProperty(as);
				RequestDispatcher rd=request.getRequestDispatcher(nextPage);
				rd.forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
