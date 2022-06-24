package log4j;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
public class SwitchCaseTravel {
	
	static Logger logger=Logger.getLogger(SwitchCaseTravel.class);
	
	public static void main(String[] args) {
		
		String log4jConfigFile=System.getProperty("user.dir")+File.separator+"log4j.Properties";
		
		PropertyConfigurator.configure(log4jConfigFile);
		
		logger.warn("Welcome to Sundara Travels.............");
		
		int date,month,year,hour,minute,shour,sminute,ehour,eminute;
		
		double distance,speed;
		
		Scanner s=new Scanner(System.in);
		
		logger.info("Enter Company start Hour....................");
		
		shour=s.nextInt();
		
		logger.info("Enter Company start Minutes....................");
		
		sminute=s.nextInt();
		
		logger.info("Enter Company end Hour..................");
		
		ehour=s.nextInt();
		
		logger.info("Enter Company end Minutes....................");
		
		eminute=s.nextInt();
		
		try {
		
		LocalTime ste=LocalTime.of(ehour, eminute);
		
		LocalTime sts=LocalTime.of(shour, sminute);
		
		logger.info("Enter Travel start Date....................");
		
		date=s.nextInt();
		
		logger.info("Enter Travel start Month....................");
		
		month=s.nextInt();
		
		logger.info("Enter Travel start Year....................");
		
		year=s.nextInt();
		
		LocalDate dfg=LocalDate.of(year, month, date);
		
		DayOfWeek sfg=dfg.getDayOfWeek();
		
		if(DayOfWeek.SUNDAY.equals(sfg)
				|| ((dfg.getMonthValue()==1)&&(dfg.getDayOfMonth()==1))
				||((dfg.getMonthValue()==1)&&(dfg.getDayOfMonth()==26))
				||((dfg.getMonthValue()==8)&&(dfg.getDayOfMonth()==15))
				) {
			
			logger.error("It is Holiday..................");
			
		}
		
		else {
		
		logger.info("Enter Bus Starting in the Trip Hour....................");
		
		hour=s.nextInt();
		
		logger.info("Enter Bus Starting in the Trip Minutes....................");
		
		minute=s.nextInt();
		
		LocalDateTime l=LocalDateTime.of(year, month, date, hour, minute);
		
		ZoneId st= ZoneId.of("Asia/Kolkata");
		
		DateTimeFormatter dtt=DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mma z");
		
		ZonedDateTime l36=l.atZone(st);
		
		String ss=dtt.format(l36);
		
		logger.info("The Start Time is....."+ss);
		
		logger.info(l.getDayOfWeek());
		
		logger.info("Enter Travel Distance in KM...........");
		
		distance=s.nextDouble();
		
		//logger.info("Distance is..........."+distance);
		
		logger.info("Enter Speed in Hours...........");
		
		speed=s.nextDouble();
		
		//logger.info("Speed in..........."+speed);
		
		double t=distance*60/speed;
		
		//logger.info("The Total Travel Time in minutes.........."+t);
		
		LocalTime sti=LocalTime.of(hour, minute);
		
		if(sti.getHour()<sts.getHour() || ((sti.getHour()==sts.getHour()) && (sti.getMinute()<sts.getMinute()))) {
			
			logger.error("It is Wrong Timing sense................");
			
		}
		
		else {
		
		if(sti.getHour()>ste.getHour() || ((sti.getHour()==ste.getHour()) && (sti.getMinute()>ste.getMinute()))) {
			
			logger.error("It is Wrong Timing sense................");
			
		}
		
		else {
		
		LocalTime startl=LocalTime.now().withHour(shour).withMinute(sminute);
		
		LocalTime endl=LocalTime.now().withHour(ehour).withMinute(eminute);
		
		long sd=ChronoUnit.MINUTES.between(startl, endl);
		
		double ssd=t/sd;
		
		double ssde=t%sd;
		
		for(int i=0;i<=ssd;i++) {
			
			DayOfWeek Day=l.getDayOfWeek().plus(i);
			
			switch (Day) {
			case SUNDAY:{
				ssd++;
				break;
			}
			case SATURDAY:{
				if(((l.getMonthValue()==1)&&(l.getDayOfMonth()==1))
						||((l.getMonthValue()==1)&&(l.getDayOfMonth()==26))
						||((l.getMonthValue()==8)&&(l.getDayOfMonth()==15))){
					ssd++;
				}
				else {
				ssd=ssd+0.5;}
				break;
			}	
			default:{
				if(((l.getMonthValue()==1)&&(l.getDayOfMonth()==1))
						||((l.getMonthValue()==1)&&(l.getDayOfMonth()==26))
						||((l.getMonthValue()==8)&&(l.getDayOfMonth()==15))){
					ssd++;
				}
				break;
			}
			}
			
		}
		
		LocalDateTime l5=l.plusDays((long) ssd).plusMinutes((long) ssde);
		
		if(l5.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			
			LocalDateTime lf=l5.plusDays(1);
			
			int ret=0;
			
			if(((lf.getMonthValue()==1)&&(lf.getDayOfMonth()==1))
					||((lf.getMonthValue()==1)&&(lf.getDayOfMonth()==26))
					||((lf.getMonthValue()==8)&&(lf.getDayOfMonth()==15))
					){
				
				ret++;
				
			}
			LocalDateTime lfg=lf.plusDays((long)ret);
			
			ZonedDateTime l37=lfg.atZone(st);
			
			String sss=dtt.format(l37);
			
			logger.debug("End International Time is............."+sss);
			
			logger.debug(lf.getDayOfWeek());
			
		}
		
		else {
			
			if(l5.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
				LocalDateTime lffd=l5.minusMinutes(sd/2);
				
				ZonedDateTime l37=lffd.atZone(st);
				
				String sss=dtt.format(l37);
				
				logger.debug("Trip End Time is............."+sss);
				
				logger.debug(l5.getDayOfWeek());
				
			}else {
			
		ZonedDateTime l37=l5.atZone(st);
		
		String sss=dtt.format(l37);
		
		logger.debug("Trip End Time is............."+sss);
		
		logger.debug(l5.getDayOfWeek());
		
		}}
		
		s.close();
		
		logger.warn("Ended...............");
		
		}
		
		}
		
		}
		}catch(Exception e) {logger.error(e);}
	}
	
}
