package api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Synchron_Demo
{	
	public static void main(String[] args) {
		
	
	Adition at=new Adition();
	ExecutorService es=Executors.newFixedThreadPool(4);
	es.execute(
			()->{
				//synchronized(at) {
					Thread.currentThread().setName("Ajith");
					at.ApplyAdition();
					at.Aditionlocation();
					at.AtendAdition();
					at.exit();
				//}
			});
	es.execute(
			()->{
				//synchronized(at) {
					Thread.currentThread().setName("Vikram");
					at.ApplyAdition();
					at.Aditionlocation();
					at.AtendAdition();
					at.exit();
				//}
			});
	es.execute(
			()->{
				//synchronized(at) {
					Thread.currentThread().setName("Vijay");
					at.ApplyAdition();
					at.Aditionlocation();
					at.AtendAdition();
					at.exit();
				//}
			});
	}
}
		
class Adition
{
	synchronized public void ApplyAdition()
	{
		Thread thr= Thread.currentThread();
		String name=thr.getName();
		System.out.println(name+" Apply Adition...");
		try {Thread.sleep(3000);}catch (Exception e) {}
		System.out.println("Apply process completed...");
		
		
		
	}
	synchronized public void Aditionlocation()
	{
		Thread thr1=Thread.currentThread();
		String name=thr1.getName();
		System.out.println(name +" Traveling that location....");
		try {Thread.sleep(5000);}catch (Exception e) {}
		System.out.println(name + "Reached that location...");
	}
	synchronized public void AtendAdition()
	{
		Thread th2=Thread.currentThread();
		String name=th2.getName();
		System.out.println(name+" Enter the Aditon room...");
		try {Thread.sleep(3000);}catch (Exception e) {}
		System.out.println(name+" Adition Finished...");
	}
	synchronized public void exit() 
	{
		Thread t=Thread.currentThread();
		String name=t.getName();
		System.out.println(name+" exit...");
	}
	
}