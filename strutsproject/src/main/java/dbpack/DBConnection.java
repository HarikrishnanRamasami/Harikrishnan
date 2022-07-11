package dbpack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dtopack.UserDTO;


public class DBConnection {
	public DBConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	public Boolean checkUser(String Name,String Password)
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping", "root", "cristiano");
			String query="select *from Users where Name=? and Password=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, Name);
			ps.setString(2,Password);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Boolean checkFlag(String Name )
	{
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping", "root", "cristiano");
			String query="select Flag from Users where Name=? ";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, Name);
			ResultSet rs=ps.executeQuery();
			
			
			
			if(rs.next())
			{
				int flag=rs.getInt(1);
				if(flag==0)
				{
				return true;
				}
				else
				{
				return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	

	
public Boolean updateFlag(String Name, int Flag) {
		
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping","root","cristiano");
			
			String query="Update Users set Flag=? where Name=?";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1, Flag);
			
			ps.setString(2, Name);
			
			int i=ps.executeUpdate();
			if(i!=0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
			
			
		}
		
	}
public Boolean registerUser(UserDTO user)

{
	try
	{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping", "root", "cristiano");
		String query="insert into Users values(?,?,?)";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, user.getName());
		ps.setString(2, user.getPassword());
		ps.setInt(3,user.getFlag());
		int i=ps.executeUpdate() ;
		
		if(i>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	catch(SQLException e)
	{
		e.printStackTrace();
		return false;
	}
	
}


	public static void main(String[] args) {
		DBConnection conn=new DBConnection();
		
		System.out.println(conn.checkUser("Aji","mind"));
		conn.updateFlag("Aji", 0);
		System.out.println(conn.checkFlag("Hari"));
		
	}

}
