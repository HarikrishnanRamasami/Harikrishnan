package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/E4")
public class Example4_copy extends HttpServlet {

    Connection con;
    PreparedStatement st;
    public void init(ServletConfig config) throws ServletException 
    {
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Actors","root","cristiano");
           st = con.prepareStatement("update details set Name=? where Age=?");
           
           }           catch( Exception e)
           { 
        	   e.printStackTrace();
           }  
        }
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException    {
        // first, set the "content type" header of the response
        String s1 = req.getParameter("name");
        String s2 = req.getParameter("age");
//        String s3 =req.getParameter("roll");
        
       res.setContentType("text/html");
       PrintWriter pw = res.getWriter();
       pw.println("<H1> Inserting Record:  </H1>");
       int x = Integer.parseInt(s2);
         try {
        		
     		while(true) {
     			Scanner scan=new Scanner(System.in);
     			System.out.println("Please enter the uid..:");
     			int uid=scan.nextInt();
     			System.out.println("Please enter the flag value...:");
     			int flag=scan.nextInt();
     	
            st.setString(1,s1);
            st.setInt(2,x);
//            st.setString(3,s3);
            st.executeUpdate();
     		}
     		}catch(Exception e) { }
          pw.println("<h2> BYE  </h2>");
        } 
    }
