package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Language {
public static void main(String[] args) {
	try {
//		BufferedReader bfr=new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\harikrishnan.r\\Pictures\\Saved Pictures\\languag.txt")));
/*		FileOutputStream fos=new FileOutputStream*/
		File file = new File("C:\\Users\\harikrishnan.r\\Pictures\\Saved Pictures\\languag.txt");
		String data=("C:\\Users\\harikrishnan.r\\Pictures\\Saved Pictures\\L2.txt");
		FileUtils.writeStringToFile(file, data);
		String name;
		
		while((name=bfr.readLine())!=null) {
			
		System.out.print(name+" ");
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
}
}
