package tom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




//@WebServlet("*.do")
//@WebInitParam(name="hari" ,value="actor")
@WebServlet(
		
		urlPatterns = "*.do",
		initParams = {@WebInitParam(name="hari",value="actor"),
				@WebInitParam(name="heathledger",value="jocker actor")
		}
		
		)
public class First extends HttpServlet {
	public void init(ServletConfig config)throws ServletException {
		String myconfig=config.getInitParameter("hari");

		System.out.println("called init....");
		System.out.println(myconfig);
		String myyconfig=config.getInitParameter("heathledger");
		System.out.println(myyconfig);
		ServletContext application=config.getServletContext();
		application.setAttribute("mygloble", "sun......!");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("uname");
		String upass=request.getParameter("upass");
		
		PrintWriter out=response.getWriter();
		
		if(name.equals("downey"))
		{
			out.println("<h1>Welcome to the marvel downey....</h>");
		}
		else
		{
//			response.sendRedirect("bradpitt.html");
			RequestDispatcher rd=request.getRequestDispatcher("bradpitt.html");
			rd.forward(request, response);
		}
		System.out.println("I Am A Marvel Actor.....!");
	}
	public void destroy() {
		System.out.println("destroyed called.......");
		
	}

}
