package jundemos;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class Garbage_Soft_Weak 
{
	public static void main(String[] args)
	{
		Runtime r=Runtime.getRuntime();
		System.out.println("Available Memory..:"+r.maxMemory());
		System.out.println("Available Processors..:"+r.availableProcessors());
		System.out.println("Free Memory..:"+r.freeMemory());
		r.gc();
		Food_Demo food_Demo=new Food_Demo();
		System.out.println("Free Memory..:"+r.freeMemory());
		//SoftReference soft=new SoftReference(food);
		//WeakReference weak=new WeakReference(food);
		//food=null;
		
		
		r.gc();
		System.out.println("Free Memory..:"+r.freeMemory());
	//	food=(Food)soft.get();
	//	food=(Food)weak.get();
		System.out.println(food_Demo);

	}
}
class Food{
	@Override
	protected void finalize() throws Throwable
	{
		System.out.println("Amount setle finalize...!");
	}
}
