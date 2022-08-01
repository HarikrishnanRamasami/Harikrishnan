package dbpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbpack.UserDIO;

public class DBCon {
	
	public DBCon() {

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		}

	}
	
	public boolean checkUser(String uname, String upass) {
		
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/anoud","root","cristiano");
			
			String query="select * from shoppingpage where uname=? and upass=?";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1, uname);
			
			ps.setString(2, upass);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				return true;
				
			}else {
				
				return false;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	public boolean checkFlag(String uname) {
		
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/anoud","root","cristiano");
			
			String query="select flag from shoppingpage where uname=?";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1, uname);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				int f=rs.getInt(1);
				
				if(f==0) {
					
					return true;
					
				}else {
					
					return false;
				
				}
				
			}else {
				
				return false;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	public boolean updateFlag(int flag,String uname ) {
		
		try {
		
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/anoud","root","cristiano");
		
			String query="Update shoppingpage set flag=? where uname=?";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1, flag);
			
			ps.setString(2, uname);
			
			int i=ps.executeUpdate();
			
			if(i!=0) {
				
				return true;
				
			}else {
			
				return false;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
public boolean RegisterUser(UserDIO user) {
		
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/anoud","root","cristiano");
			
			String query="insert into shoppingpage values(?,?,?)";
			
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1, user.getUname());
			
			ps.setString(2, user.getUpass());
			
			ps.setInt(3, 0);
			
			int rs=ps.executeUpdate();
			
			if(rs>0) {
				
				return true;
				
			}else {
				
				return false;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	public static void main(String[] args) {
		
		DBCon db=new DBCon();
		
		UserDIO user=new UserDIO();
		
		user.setFlag(0);
		
		user.setUname("Hari");
		
		user.setUpass("mind");
		
		System.out.println(db.checkUser("Hari", "mind"));
		
		System.out.println(db.checkFlag("Aji"));
		
		System.out.println(db.updateFlag(1,"Aji"));
		
		System.out.println(db.RegisterUser(user));
		
	
	}

}