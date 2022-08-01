package api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Rolex_Dili_Kfc 
{
	public static void main(String[] args)
	{
		Kfc kfc=new Kfc();
		ExecutorService es=Executors.newFixedThreadPool(1);
		es.execute(
					()->
					{
						Thread.currentThread().setName("Ronaldo");
						kfc.fd.Order();
					}
				  );
	}
}

class FoodCountre
{
	public void Order()
	{
		
	
	Thread thread=Thread.currentThread();
	String name=thread.getName();
	System.out.println(name+" Order the food...!");
	try {Thread.sleep(4000);}catch (Exception e) {}
	System.out.println(name+" Recieved Order...!");
}
}

class Kfc
{
	FoodCountre fd=new FoodCountre();
}