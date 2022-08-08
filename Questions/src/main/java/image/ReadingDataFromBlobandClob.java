package image;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class ReadingDataFromBlobandClob {
   public static void main(String args[]) throws Exception {
      //Registering the Driver
    Class.forName("com.mysql.cj.jdbc.Driver");
      //Getting the connection
     
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/actors", "root", "cristiano");
      System.out.println("Connection established......");
      //Inserting values
      String query = "INSERT INTO Image(Name, Logo) VALUES (?, ?)";
      PreparedStatement pstmt = con.prepareStatement(query);
     pstmt.setString(1, "Dhoni");
//      FileReader fileReader = new FileReader("C:\\Users\\harikrishnan.r\\Pictures\\images.txt");
//      pstmt.setClob(2, fileReader);
      InputStream inputStream = new FileInputStream("C:\\Users\\harikrishnan.r\\Pictures\\images.jpg");
      pstmt.setBlob(2, inputStream);
      pstmt.execute();
      System.out.println("Record inserted......");
      //Retrieving the results
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * from Image");
      while(rs.next()) {
         String name = rs.getString("Name");
//         Clob clob = rs.getClob("Article");
         Blob blob = rs.getBlob("Logo");
         System.out.println("Name: "+name);
      //   System.out.println("Clob value: "+clob);
         System.out.println("Blob value: "+blob);
         System.out.println("");
         System.out.print("Clob data is stored at: ");
         //Storing clob to a file
         int i, j =0;
//         Reader r = clob.getCharacterStream();
//         String filePath = "C:\\output\\"+name+"_article_content.txt";
//         FileWriter writer = new FileWriter(filePath);
//         while ((i=r.read())!=-1) {
//               writer.write(i);
//        }
//         writer.close();
//         System.out.println(filePath);
//         j++;
    //     System.out.print("Blob data is stored at: ");
//         InputStream is = blob.getBinaryStream();
//         byte byteArray[] = new byte[is.available()];
//         is.read(byteArray);
    //     filePath = "E:\\output\\"+name+"_article_logo.jpg";
       //  FileOutputStream outPutStream = new FileOutputStream(filePath);
     //    outPutStream.write(byteArray);
      //   System.out.println(filePath);
      }
   }
}