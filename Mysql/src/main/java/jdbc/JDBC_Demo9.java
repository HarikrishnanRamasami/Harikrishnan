package jdbc;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JDBC_Demo9 {
	static Logger logger=Logger.getLogger(JDBC_Demo9.class);
	public static void main(String[] args)throws Exception {
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel","root","cristiano");
		
		String sql="{call proc2(?,?)}";
		
		CallableStatement cs=con.prepareCall(sql);
		cs.setString(1,"Chicken biriyani");
		cs.setInt(2,150);
		cs.execute();
		
		int noofchanges=cs.executeUpdate();
		
		logger.info(noofchanges);
		
		
	}
	

}
