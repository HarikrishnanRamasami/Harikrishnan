package utildemo;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class List_Demo
{
	public static void main(String[] args) 
	{
		List<String>str=new ArrayList<>();
		str.add("Hari");
		str.add("Vijay");
		str.add("Rolex");
		System.out.println(str);
		
//		str.indexOf(1);
//		str.remove(0);
//		str.clear();
		
		/*
		 * Its add the set of all values
		 */
		boolean b=Collections.addAll(str,"Kaithi","Vikram","jd","Sedu");
		System.out.println(b);
		System.out.println(str);
//		for(int i=0;i<str.size();i++)
//		{
//			System.out.println(str.get(i));
//		}
		System.out.println("############################################################################");
		for(String m:str)
		{
			System.out.println(m);
		}
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//Iterator<String> itrat=str.iterator();
	
		{
			
		}
//		
//		while(itrat.hasNext())
//		{
//			System.out.println(itrat.next()); 
//		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		ListIterator<String> lt=str.listIterator();
		while(lt.hasNext()) {
			System.out.println(lt.next());
		}
		System.out.println("//////////////////////////////////////////////////////////////////////////");
		
		while(lt.hasPrevious())
		{
			System.out.println(lt.previous());
		}
	}
}
