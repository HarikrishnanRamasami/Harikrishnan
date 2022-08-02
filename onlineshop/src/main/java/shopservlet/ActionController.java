package shopservlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("*.do")
public class ActionController extends HttpServlet {
	
	ResponsePage rs;
	
	@Override
	public void init(ServletConfig con) throws ServletException {
		
		rs=new ResponsePage();
		
		String path=con.getServletContext().getRealPath("/WEB-INF/config.properties");
		
		con.getServletContext().setAttribute("path", path);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setMaxInactiveInterval(30);
		rs.process(request, response);
		
	}
	
	@Override
	public void destroy() {
		
		System.out.println("Old deleted.............");
		
	}

}