package tom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Second
 */
@WebServlet("/Second")
public class Second extends HttpServlet {
	public void init(ServletConfig config) {
		ServletContext app=config.getServletContext();
		System.out.println("From second servlet..."+app.getAttribute("mygloble"));	
//		System.out.println(app.getRealPath(""));
		try {
			String path=app.getRealPath("/WEB-INF/config.properties");
			Properties prop=new Properties();
			prop.load(new FileInputStream(path));
			System.out.println(prop.get("hello"));
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	}

}
