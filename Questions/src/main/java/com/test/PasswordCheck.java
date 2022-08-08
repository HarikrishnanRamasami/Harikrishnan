package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasswordCheck {
public static boolean checkupass(String Name,String Password) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping","root","cristiano");
		String sql="Select * From users where Name=? and Password=?";
		PreparedStatement prs=con.prepareStatement(sql);
		prs.setString(1,Name);
		prs.setString(2,Password);
		
		ResultSet res=prs.executeQuery();
		
		if(res.next()) {
			return true;
			
		}
		else
		{
			return false;
		}
	}catch (Exception e) {
		e.printStackTrace();
		
	}
	return false;
}
public static void main(String[] args) {
	System.out.println(PasswordCheck.checkupass("Hari","Rolex"));
	
}
}
