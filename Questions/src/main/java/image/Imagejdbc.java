package image;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class Imagejdbc {
	
	public void createtable() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/actors", "root",
					"cristiano");
			String ctable = "create table Image(Name varchar(255),Logo blob)";
			Statement statement = connection.createStatement();
			statement.execute(ctable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

public void inserttablevalue() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/actors", "root",
					"cristiano");
			String insertvalues = "insert into Image(Name,Logo) values(?,?)";

			FileInputStream fis = new FileInputStream("C:\\Users\\harikrishnan.r\\Pictures\\images.jpg");
			PreparedStatement prs = connection.prepareStatement(insertvalues);
			prs.setString(1, "Dhoni");
			prs.setBinaryStream(2, fis);
			prs.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public void selecttable() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/actors", "root",
					"cristiano");
			String insertvalues = "select*from Image";
			Statement stm=connection.createStatement();
			FileInputStream fis = new FileInputStream("C:\\Users\\harikrishnan.r\\Pictures\\images.jpg");
			PreparedStatement prs = connection.prepareStatement(insertvalues);
			prs.execute();
			ResultSet rs=stm.executeQuery(insertvalues);
			while(rs.next()) {
				System.out.println("Name :"+rs.getString("Name")+"  "+"Image :"+rs.getBlob("Logo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public void Display(){
	
	
}
public static void main(String[] args) {
	Imagejdbc gt=new Imagejdbc();
	gt.selecttable();

}
}