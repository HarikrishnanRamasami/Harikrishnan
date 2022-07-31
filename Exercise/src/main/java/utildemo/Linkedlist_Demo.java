package utildemo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Linkedlist_Demo
{
	public static void main(String[] args)
	{
		LinkedList<String> ll=new LinkedList<>();
		ll.add("Hari");
		ll.add("Cillian");
		ll.add("Jocker");
		boolean bn=Collections.addAll(ll,"Dhoni","Johnny","Molver","");
//		ll.forEach(System.out::println);
//		ll.forEach(new Mylink());
		ll.forEach((t)->{System.out.println(t);});
	}


	static void print(String t)
	{
		System.out.println(t);
	}
}
class Mylink implements Consumer<String>
{
	 @Override
	public void accept(String t)
	{
		 System.out.println(t);
	}
}