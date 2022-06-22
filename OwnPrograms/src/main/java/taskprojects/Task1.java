package taskprojects;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Task1 {
	static Logger logger=Logger.getLogger(Task1.class);
	public static void main(String[] args){
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		
		Flight flight=new Flight();
		
		flight.Coman();
	}
}

	

class Flight{
	
	static Logger logger=Logger.getLogger(Flight.class);
	 //import scanner:
	Scanner scanner=new Scanner(System.in);
	
	public void Calendar()
	{
		logger.info("Enter the Date.. :");
		int Date=scanner.nextInt();
		
		logger.info("Enter the Month.. :");
		int Month=scanner.nextInt();
		
		logger.info("Enter the Year.. :");
		int Year=scanner.nextInt();
		
		
		//import LocalDate : 
		LocalDate localdate=LocalDate.of(Date,Month,Year);
		//import DateTimeFormatter
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("dd-MM-yyyy"); 
        // Date date = new Date(Date-Month-Year);
	        
	    // String d1 = formats.format(localdate);
	       	
        //convert Format
        String formatDateTime =localdate.format(formats); 
		logger.info("Starting Date :"+formatDateTime);
	
	
		if (( Year%400 == 0)|| (( Year%4 == 0 ) &&( Year%100 != 0))) {
			System.out.format("\n %d is a Leap Year. \n", Year);
		}
		else {
			System.out.format("\n %d is NOT a Leap Year. \n", Year);
		}
	}	
	public void Kilometers() 
	{
		logger.info("Enter the Distance.. :");
		int Distance=scanner.nextInt();
		
		logger.info("Enter the Speed.. :");
		double Speed =scanner.nextInt();
		
		double time=Distance/Speed;
		
		logger.info("Total Travel Time..:"+time+"hrs");
		
		
	}
	public void Time()
	{
		logger.info("Enter the Hours.. :");
		int Hours=scanner.nextInt();
		
		logger.info("Enter the Minutes... :");
		int Minutes=scanner.nextInt();
		 
		//import LocalTime :
		LocalTime localtime=LocalTime.of(Hours, Minutes);
		DateTimeFormatter formats = DateTimeFormatter.ofPattern("HH:mm a"); 
		String formatDateTime =localtime.format(formats); 
		
		logger.info(formats.format(localtime));
		Kilometers();
		
		
	}
	public void Coman()
	{
		Calendar();
		//Time();
		//Leap_Year();
	
		
	}
}