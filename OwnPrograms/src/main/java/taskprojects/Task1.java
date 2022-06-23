package taskprojects;


import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Task1 
{
	static Logger logger=Logger.getLogger(Task1.class);
	public static void main(String[] args)
	{
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);		
		Flight flight=new Flight();
		flight.Coman();
	}
}
//Another class Created
class Flight
{
	static Logger logger=Logger.getLogger(Flight.class);
	 //import scanner:
	Scanner scanner=new Scanner(System.in);	
	public void Calendar()
	{
		logger.info("Date :");
		int Date=scanner.nextInt();
		logger.info("Month :");
		int Month=scanner.nextInt();
		logger.info("Year :");
		int Year=scanner.nextInt();
		//import LocalDate : 
		LocalDate localdate=LocalDate.of(Year,Month,Date);
		//import DateTimeFormatter
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("dd-MM-yyyy");    	
        //convert Format
        String formatDateTime =localdate.format(formats); 
		logger.info("Starting Date :"+formatDateTime);
        // Massage passing
		logger.info("You Want Know A Leap Year"+"\n");
		String str;
		logger.info("Yes   (or)   No"+"\n");
		str=scanner.next();
		
		if(str.equals("Yes"))
		{
		 if (( Year%400 == 0)|| (( Year%4 == 0 ) &&( Year%100 != 0)))
		 {
			 System.out.format("\n %d is a Leap Year. \n", Year);
		 }
		 else 
		 {
			 System.out.format("\n %d is NOT a Leap Year. \n", Year);
		 }
		 if(str.equals("No"))
		 {
			 logger.info("Thank You");
		 }
		 }
		//Reference Method
		Time();
	}
	
	public void Kilometers() 
	{
		logger.info("Total Distance.. :");
		int Distance=scanner.nextInt();	
		logger.info("Speed.. :");
		double Speed =scanner.nextInt();
		double time=Distance/Speed;
		int convint=(int)time;
		//LocalDateTime el=localdate.plusMinutes(convint);
		logger.info("Total Travel Time...:"+time+"hrs");	
	}
	
	public void Time()
	{
		logger.info("Hours :");
		int Hours=scanner.nextInt();
		logger.info("Minutes :");
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
	}
}