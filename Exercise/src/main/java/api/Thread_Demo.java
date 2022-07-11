package api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread_Demo {
public static void main(String[] args) {
	System.out.println("Buy the ticket...");
	ExecutorService es=Executors.newFixedThreadPool(4);
	es.execute(()->{Book();Exit();});
	es.shutdown();
	try{
		Thread.sleep(2000);
	}catch (Exception e) {}
	System.out.println("Movie started...!");
	try{
		Thread.sleep(2000);
	}catch (Exception e) {}
	System.out.println("Movie end..!");
	
}
public static void Book() {
	try 
	{
		Thread.sleep(5000);
	}catch(Exception e) {
	}

	System.out.println("Collect the ticket...!");
}
public static void Exit() {
	try {
		Thread.sleep(4000);
	}catch (Exception e) {}
	System.out.println("Exit");
}
}
