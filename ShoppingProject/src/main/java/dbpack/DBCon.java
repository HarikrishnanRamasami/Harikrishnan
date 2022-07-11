package dbpack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.ConnectionEvent;

import dtopack.UserDTO;

public class DBCon
{
	public DBCon() 
	{
		try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch (Exception e)
			{
				e.printStackTrace(); 
			}
	}


public boolean checkUser(String Name,String Password)
{
	try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping","root","cristiano");
			PreparedStatement ps=con.prepareStatement("Select*from Users where Name=? and Password=?");
			ps.setString(1, Name);
			ps.setString(2, Password);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
}
public boolean checkFlag(String Name)
{
	try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping","root","cristiano");
			PreparedStatement ps=con.prepareStatement("Select Flag from Users where Name=?");
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
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
}
public boolean updateFlag(String Name,int Flag)
{
	try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping","root","cristiano");
			PreparedStatement ps=con.prepareStatement("update Users set Flag=? where Name=?");
			ps.setInt(1,Flag);
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
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	
}
public boolean registerUser(UserDTO user)
{
	try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping","root","cristiano");
			PreparedStatement ps=con.prepareStatement("insert into Users values(?,?,?)");
			ps.setString(1,user.getName());
			ps.setString(2,user.getPassword());
			ps.setInt(3,user.getFlag());
			int i=ps.executeUpdate();
			if(i>0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
}


public static void main(String[] args) 
{
	DBCon dbcon=new DBCon();
	
	System.out.println(dbcon.checkUser("Aji","mind"));
	System.out.println(dbcon.checkFlag("Aji"));
	dbcon.updateFlag("Aji",0);
	}
}