package examples;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/rolex")
public class Example1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String name=request.getParameter("exampl1");
		pw.println("<html>");
		pw.println("<head><title>Hello"+name+"</title></head>");
		pw.println("<body>");
		pw.println("<h1> Hello,<blink>"+name+"</blink></head>");
		pw.println("</body>");
		pw.println("</html>");
	}

}
