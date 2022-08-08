package com.test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class IopackUrl {
public static void main(String[] args) {
	try {
		URL url=new URL("https://en.wikipedia.org/wiki/Cristiano_Ronaldo");
		URLConnection con=url.openConnection();
		
		BufferedInputStream bi=new BufferedInputStream(con.getInputStream());
		FileOutputStream fos=new FileOutputStream("google.html");
		
		byte[] b=new byte[150];
		int noofrows=0;
		while((noofrows=bi.read(b))!=-1) {
			fos.write(b,0,noofrows);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
}
}
