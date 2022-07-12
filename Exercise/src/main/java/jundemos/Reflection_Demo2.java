package jundemos;

import java.lang.reflect.Method;

public class Reflection_Demo2 
{
	public static void main(String[] args) throws Exception
	{
		Demo demo=new Demo();
		System.out.println(demo);
		
		Demo demo1=(Demo)Class.forName("jundemos.Demo").newInstance();
		System.out.println(demo1);
		
		demo1=(Demo)Class.forName("jundemos.Demo").getConstructor().newInstance();
		System.out.println(demo1);
		
		demo1=(Demo)Class.forName("jundemo.Demo").getConstructor(String.class,int.class).newInstance("Hello",100);
		System.out.println(demo1);
	}
}
class Demo
{
	public Demo()
	{
		System.out.println("empty constructor....");
	}
	public Demo(String str,int i)
	{
		System.out.println(str+"..............."+i);
	}
	public void met(String str,Employee ee)
	{
		System.out.println(str+".........."+ee);
	}
}
class Employee
{
	public Employee() {
		System.out.println("Employee object created...!");
	}
}