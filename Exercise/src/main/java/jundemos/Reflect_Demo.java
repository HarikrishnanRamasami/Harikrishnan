package jundemos;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflect_Demo
{
	public static void main(String[] args)throws Exception
	{
		PoliceStation police=new PoliceStation();
		Politician pl=new Politician();
		
		police.Investication(pl);
	}
}
class PoliceStation
{
	public void Investication(Object Obj)throws Exception
	{
	//	Politician pt=(Politician)Obj;
//		System.out.println(pt.name);
		//System.out.println(pt.secretwork);
		Class c=Obj.getClass();
		Field field=c.getField("name");
		System.out.println(field.get(Obj));
		
		Method method=c.getMethod("SocialService");
		
		method.invoke(Obj);
		Cbi(Obj);
		
	}
	public void Cbi(Object Obj)throws Exception
	{
		Class c=Obj.getClass();
		Field field=c.getDeclaredField("secretwork");
		field.setAccessible(true);
		System.out.println(field.get(Obj));
		
		Method method=c.getDeclaredMethod("SecretService");
		method.setAccessible(true);
		method.invoke(Obj);
	}
}
class Politician
{
	public String name="I am a Robert downey jr....!";
	private String secretwork="I am a Cocaine dealer.....! ";
	
	public void SocialService(Object Obj)
	{
		System.out.println("I am a Marvel Actor....!");
	}
	private void SecretService(Object obj)
	{
		System.out.println("I do a Cocaine Sell.....!");
	}
}