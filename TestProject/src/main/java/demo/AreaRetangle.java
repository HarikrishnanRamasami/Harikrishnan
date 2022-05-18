package demo;

import java.io.File;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AreaRetangle {
	static Logger logger=Logger.getLogger(AreaRetangle.class);
	public static void main(String[] args) {
		//logger printing types
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		
		Scanner sc=new Scanner(System.in);
		logger.info("Enter length of Retangle :");
		double length=sc.nextDouble();
		logger.info("Enter the length of Width :");
		double width=sc.nextDouble();
		
	    double result=(length*width)/2;
		{
			logger.info("Area Retangle is :"+ result);
		}
	}

}
