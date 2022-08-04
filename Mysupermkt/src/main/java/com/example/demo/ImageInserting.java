package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageInserting {

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shop","root","cristiano");

            System.out.println(con);

            String qury="insert into mobile values(?,?,?)";

            PreparedStatement ps=con.prepareStatement(qury);

            ps.setString(1, "Iphone14");

            FileInputStream fis=new FileInputStream("C:\\Users\\Hari D'cruz\\Pictures\\ipho.jpg");

            ps.setBinaryStream(3, fis, fis.available());

            ps.setString(2, "1000");

            int a=ps.executeUpdate();

            System.out.println(a);

        } catch (ClassNotFoundException | SQLException | IOException e) {

            e.printStackTrace();

        }

    }

}