package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Util_Propertie {
	public static void main(String[] args) throws IOException {
//		Properties prop = new Properties();
//		prop.put("200", "michale");
//		prop.put("100", "sachine");
//		prop.put("400", "dhoni");
//		prop.put("300", "cristiano");
//		prop.put("300", "cristiano");
//
//		System.out.println(prop);
//
//		Set set = prop.entrySet();
//
//		Iterator iter = set.iterator();
//
//		while (iter.hasNext()) {
//			Map.Entry me = (Map.Entry) iter.next();
//			
//			System.out.println(me);
//		}
		
		
	     Properties prop = readPropertiesFile("config.properties");
	      System.out.println("name: "+ prop.getProperty("name"));
	
	   }
	   public static Properties readPropertiesFile(String fileName) throws IOException {
	      FileInputStream fis = null;
	      Properties prop = null;
	      try {
	         fis = new FileInputStream(fileName);
	         prop = new Properties();
	         prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException e) {
	         e.printStackTrace();
	      } finally {
	         fis.close();
	      }
	      return prop;
	   }

	}

