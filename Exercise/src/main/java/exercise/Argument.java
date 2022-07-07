package exercise;

import java.io.File;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Argument 
     {
	static Logger logger=Logger.getLogger(Argument.class);
      public static void main(String[] args) 
           {  
    	    String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
        	PropertyConfigurator.configure(log4jConfigFile);
            Practice practice=new Practice();
            practice.Movies("Vijay",64,100);
            int[] n= {55,27,47,37};
            String[] s={"Sivakarthikeyan","Priyanga mohan","Cillian Murphy","Emilia clark"};
            practice.Chennai(s,n);
           }
     }

class Practice
   {
	static Logger logger=Logger.getLogger(Argument.class);
public void Movies(String m,int n,double d) 
     {
	   logger.info("Name :"+m);
	   logger.info("No Of Movies :"+n);
	   logger.info("Per Movie Income :"+d+"Cr");
     }	
public void Chennai(String[] s,int n[]) 
     {
	  logger.info(s+" : "+n);
     }
}