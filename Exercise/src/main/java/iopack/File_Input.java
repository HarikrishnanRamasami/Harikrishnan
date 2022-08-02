package iopack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class File_Input {
	static Logger logger=Logger.getLogger(File_Input.class);
	
	public static void main(String[] args)
	{
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		
		File file=new File("C:\\hari\\haricr\\fisdemo.txt");
	//	logger.info(file.canExecute());
		/*
		 * Its get a file path directory 
		 */
//		logger.info(file.getAbsoluteFile());
//		logger.info(file.getAbsolutePath());
		
		
/*		try (FileInputStream fis=new FileInputStream(file))
		{
			Scanner MyReader=new Scanner(fis);
			byte b[]=new byte[4];
			int bytesread=0;
			while((fis.read(b))!=-1)
			{
				logger.info(b);
				String Data=MyReader.nextLine();
				String s=new String(b,0,bytesread);
				logger.info();
				
			}
		
			
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/**
		 * insert the file into the FileReader
		 * make the copy file from input file
		**/
	
		/**try(FileReader filereader=new FileReader(file);
			FileWriter filewriter=new FileWriter("C:\\hari\\haricr\\fis1_demo.txt");)
		{
			char[] char2=new char[12];
			int fileread=0;
			while((fileread=filereader.read(char2))!=-1) {
				filewriter.write(char2,0,fileread);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}**/
		
		/***
		 * URL
		 * using BufferedReader and
		 * FileWriter 
		***/
		
/***		URLConnection urlcon=null;
		
		try {
			URL url=new URL("http://www.google.com/index.html");
			urlcon=url.openConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//  try resourse
		 
		try(
				BufferedReader br=new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
				FileWriter filewriter=new FileWriter("chrome.txt");)
		{
			char[] char1=new char[128];
			int bfl=0;
			while((bfl=br.read(char1))!=-1)
			{
				filewriter.write(char1,0,bfl);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}***/
		
		/****
		 * using BufferedInputStream and
		 * FileOutputStream
		****/
		
			URLConnection urlcon=null;
		
		try 
		{
			URL url=new URL("http://www.google.com/index.html");
			urlcon=url.openConnection();
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		try( BufferedInputStream bis=new BufferedInputStream(urlcon.getInputStream());
				FileOutputStream fos=new FileOutputStream("github.html");)
		{
			byte[] byte1=new byte[128];
			int bytesread=0;
			while((bytesread=bis.read(byte1))!=-1)
			{
				fos.write(byte1,0,bytesread);
			}
		}catch (Exception e) {
		e.printStackTrace();
	}

}
}
