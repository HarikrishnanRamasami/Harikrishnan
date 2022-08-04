package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbCon {
	
	public ResultSet checkTable(String TableName) {
		
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shop","root","cristiano");
			
			String qury="select * from "+TableName;
			
			Statement ss=con.createStatement();
			
			ResultSet rs=ss.executeQuery(qury);
			
			return rs;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
	}

}
