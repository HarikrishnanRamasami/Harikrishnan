package jundemos;

import java.lang.reflect.Method;

public class Custermer_Demo 
{
	public static void main(String[] args) throws Exception{
		Food_Demo food=new Food_Demo();
//		Veg veg=food.getClass().getAnnotation(Veg.class);
		
		Method method=food.getClass().getMethod("Chicken");
		Veg veg=method.getAnnotation(Veg.class);
		if(veg==null)
		{
			System.out.println("Non vegiterian Food...!");
		}
		else
		{
			System.out.println("Vegiterian Food...!");
		}
	}
}
