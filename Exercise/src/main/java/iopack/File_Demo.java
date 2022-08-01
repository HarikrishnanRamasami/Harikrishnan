package iopack;

import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class File_Demo {
	static Logger logger=Logger.getLogger(File_Demo.class);
	public static void main(String[] args) 
	{
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		try {
			
			FileWriter filewriter=new FileWriter("C://hari//haridcruz.txt");
			
			filewriter.write("my name downey jr .....");
			filewriter.close();
			logger.info("Succefully writed...!");
//		File file=new File("C://hari//haridcruz.txt");
//		if(file.createNewFile())
//		{
//			logger.info("File created..:"+file.getName());
//		}
//		else
//		{
//			logger.info("File already exit..:");
	//	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
