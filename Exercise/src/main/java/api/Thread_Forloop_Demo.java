package api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread_Forloop_Demo {
	public Thread_Forloop_Demo()
	{
		ExecutorService es=Executors.newFixedThreadPool(1);
		es.execute(()->{System.out.println("Completed...!");});
		es.shutdown();
	}
public static void main(String[] args) {

	for(int i=0;i<=10;i++)
	{
		System.out.println("Print the numbers...! :" + i);
		try
		{
			Thread.sleep(2000);
		}catch (Exception e) {}
	}

}

}
