package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Jdbc2 {

	public Jdbc2() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Insert() {
		try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mall","root","cristiano");
		String sql="insert into phoenix values(?,?,?)";
		
		PreparedStatement pr=con.prepareStatement(sql);
	
		pr.setString(1,"theatre");
		pr.setInt(2,3);
		pr.setInt(3,3);
		
		int nof=pr.executeUpdate();
		
		System.out.println(nof);
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void Update() {
		try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost/mall","root","cristiano");
		String sql="update phoenix set elevator=? where Floor=?";
		
		PreparedStatement pr=con.prepareStatement(sql);
		
		pr.setInt(1, 2);
		pr.setInt(2,3);
		
		int nof=pr.executeUpdate();
		
		System.out.println(nof);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void Delete() {
		try {
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mall","root","cristiano");
			String sql="delete from phoenix where Floor=3";
			Statement st=con.createStatement();
			st.execute(sql);
			System.out.println(st);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Jdbc2 j=new Jdbc2();
//		j.Insert();
//	j.Update();
		j.Delete();
	}
}
