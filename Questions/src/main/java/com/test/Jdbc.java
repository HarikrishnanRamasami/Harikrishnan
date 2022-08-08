package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Jdbc {

	public void CreateDatabase() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "cristiano");

		String sql = "create schema Mall";

		Statement st = con.createStatement();

		st.execute(sql);

		System.out.println(st);
	}

	public void CreateTable() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mall", "root", "cristiano");

			String sql = "create table phoenix (Name varchar(255),Floor int,elevator int)";

			Statement stm = con.createStatement();
			stm.execute(sql);
			System.out.println(stm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Insertvalue() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mall","root","cristiano");
		Scanner sc=new Scanner(System.in);
		String sql="insert into phoenix values(?,?,?)";
		PreparedStatement prs=con.prepareStatement(sql);
		String str="yes";
		String str2="No";
		System.out.println("You want to insert details"+" :"+str+" :"+str2);
		String conf=sc.nextLine();
		if(conf.equals(str)) {
			
		while(true) {
			System.out.println("Enter Name:");
			String Name=sc.nextLine();
			
			System.out.println("Enter Floor:");
			int Floor=sc.nextInt();
			
			System.out.println("Enter elevator:");
			int elevator=sc.nextInt();
			prs.setString(1,Name);
			prs.setInt(2,Floor);
			prs.setInt(3, elevator);
			int noofrowschanged=prs.executeUpdate();
		}	
		}
		else {
			System.out.println("Thank you...!");
		}
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpdateValue() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mall", "root", "cristiano");

			String sql = "update phoenix set Floor=? where elevator=?";
			PreparedStatement prs = con.prepareStatement(sql);
			prs.setInt(1, 1);
			prs.setInt(2, 4);
			System.out.println(prs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Retrieve() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mall", "root", "cristiano");

			String sql = "select*from phoenix";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("Name") + " " + rs.getString("Floor") + " " + rs.getString("elevator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Delete() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mall", "root", "cristiano");

			String sql = "DELETE FROM phoenix WHERE elevator=1";
			Statement stm = con.createStatement();
			stm.execute(sql);
			System.out.println(stm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Jdbc j = new Jdbc();
//		j.CreateDatabase();
//		j.CreateTable();
	
//		j.Retrieve();
		// j.UpdateValue();
		// j.Retrieve();
		// j.Delete();
		j.Insertvalue();
	}
}
