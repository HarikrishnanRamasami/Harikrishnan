package api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Inter_Thread
{
	public static void main(String[] args)
	{	Top_Gun tg=new Top_Gun();
		ExecutorService es=Executors.newFixedThreadPool(2);
		es.execute(()->{
			for (int i=0;i<1;i++)
			{
				tg.Fuel();			
			}
			
		});
		es.execute(()->{
			for (int i=0;i<1;i++)
			{
				tg.Engine();		
			}
		});
		es.shutdown();
	}
}

class Top_Gun
{
	boolean flag;
	synchronized public void Fuel()
	{
		if(flag)
		{
			try {wait();}catch (Exception e) {}
			
		}
		System.out.println("Fill the Fuel....!");
		flag=true;
		notify();
		
	}
	synchronized public void Engine()
	{
		if(!flag)
		{
			try {wait();}catch(Exception e) {}
		}
			System.out.println("Engine Strated...!");
			flag=false;
			notify();
	}
}
