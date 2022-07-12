package jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class JDBC_ResultMD {
	static Logger logger=Logger.getLogger(JDBC_ResultMD.class);
	public static void main(String[] args)throws Exception {
		String log4jConfigFile =System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Hotel","root","cristiano");
		
		String sql="select*from MenuCard where Food_List=?";
		PreparedStatement prs=con.prepareStatement(sql);
		prs.setString(1, "Parrota");
		
		ResultSet rs=prs.executeQuery();
		ResultSetMetaData rsmd=rs.getMetaData();

		int countcolumns=rsmd.getColumnCount();
		
		for(int i=1;i<=countcolumns;i++)
		{
			logger.info(rsmd.getColumnName(i));
		}
		if(rs.next())
		{
			for(int i=1;i<=countcolumns;i++)
			logger.info(rs.getString(i)+"\t");
		}
	}

}
