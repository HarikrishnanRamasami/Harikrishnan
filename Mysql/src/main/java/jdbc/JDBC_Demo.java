package jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

//logger import
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JDBC_Demo 
{
	static Logger logger=Logger.getLogger(JDBC_Demo.class);
	public static void main(String[] args) throws Exception
	{
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		
		//Step1 - Load the Driver.
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Step 2 - Establish Connection
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/anoud","root","cristiano");
			
		logger.info(con);
		//Step 3 - Execute sql statement		
		//step 4 - process result
		
/**		
  		//statement import;
  		 
  		String sql="update Actors_Table set User_ID=0";
		
		Statement statement=con.createStatement();
		int noofrowschanged=statement.executeUpdate(sql);
		logger.info(noofrowschanged); 
***/
		
/*
		//prepared statement import;
		
		String sql="update Actors_Table set User_ID=? where Age=?";
		
		PreparedStatement prs=con.prepareStatement(sql);
//		prs.setInt(1,7);
//		prs.setInt(2,59);
		prs.setInt(1,8);
		prs.setInt(2,59);

		int noofrowschanged=prs.executeUpdate();
		
		
		logger.info(noofrowschanged);
*/
		
		String sql="update Actors_Table set User_ID=? where uname=?";
		
		PreparedStatement prs=con.prepareStatement(sql);
		while(true)
		{
			Scanner scanner=new Scanner(System.in);
			logger.info("Enter the User_ID value:");
			int id=scanner.nextInt();
			
			logger.info("Enter the uname. :");
			String name=scanner.nextLine();
			prs.setInt(1,id);
			prs.setString(2,name);
			
			int noofrowschanged=prs.executeUpdate();
			
			logger.info(noofrowschanged);
		}
		
		
		}
	}

