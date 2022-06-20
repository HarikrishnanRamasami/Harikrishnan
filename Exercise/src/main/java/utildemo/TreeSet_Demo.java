package utildemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TreeSet_Demo
{
	public static void main(String[] args)
//	{
////		TreeSet<String> treeset=new TreeSet<String>(new Treesset());
//		Set treeset=new TreeSet(new Demoex());
//		
//		treeset.add(new Second( "hari"));
//		treeset.add(new Second("avenger"));
//		treeset.add(new Second("beach"));
//		treeset.add(new Second("downey"));
//		System.out.println(treeset);
//		
////		TreeSet<String> treeset2=new TreeSet<String>(new Demoex());
////		
////		treeset2.add("hari");
////		treeset2.add("avenger");
////		treeset2.add("beach");
////		treeset2.add("jk");
////		
////		System.out.println(treeset2);
//	
////		Treesset ts=new Treesset();
////		System.out.println(ts.compareTo());
//		
//	}
//}
//
//
//class Demoex implements Comparator<Second>
//{
//	@Override
//	public int compare(Second o, Second o2) {
//	System.out.println(o+""+o2);
//		return o2.compareTo(o);
//
//	}
//}
//class Second implements Comparable<Second>
//{
//	String Employeename;
////	int EmployeeID;
//	public Second(String Employeename/*,int EmployeeID*/)
//	{
//		this.Employeename=Employeename;
//		//this.EmployeeID=EmployeeID;
//	}
//	@Override
//	public int compareTo(Second second) {
//		System.out.println(second);
//		return this.Employeename.compareTo(second.Employeename);
//		
//	}
////	public int compareTo(Second second) {
////		return this.EmployeeID.compareTo(second.EmployeeID);
////	}
//	
//	
//}
	{
		List<Name,Id> lt=new ArrayList<Name,Id>();
		
		lt.add(new Name("Hari"));
		lt.add(new Name("Ronaldo"));
		lt.add(new Name("Tom Holland"));
		lt.add(new Name("Johnny depp"));
		lt.add(new Name("Robert Downey jr"));
		lt.add(new Name("Cillian Murphy"));
		lt.add(new Name("Dicaprio"));
		
		System.out.println(lt);
		
	    Collections.sort(lt);
	    
	    System.out.println(lt);
	    
	    HashSet<Name,Id> hs=new HashSet<Name,Id>(lt);
	    TreeSet<Name,Id> ts=new TreeSet<Name,Id>(new Actorname(),new ActorId());
	    ts.addAll(hs);
		
			System.out.println(ts);
		
	}
	}

class Actorname implements Comparator<Name>{
	@Override
	public int compare(Name o1,Name o2) {

		return o2.compareTo(o1);
	}
	
}

class Name implements Comparable<Name>{
	String name;
	
	public Name(String name) {
		this.name=name;
		
	}
	@Override
	public int compareTo(Name o) {
		
		return this.name.compareTo(o.name);
	
	}
	@Override
	public String toString() {
		
		return this.name;
	}
	
}

// Number CompareTo

class ActorId implements Comparator<Id>{
	@Override
	public int compare(Id o1,Id o2) {

		return o2.compareTo(o1);
	}
	
}
class Id implements Comparable<Id>{
	Integer Id;
	
	public Id(Integer Id) {
		this.Id=Id;
		
	}
	@Override
	public int compareTo(Id o) {
		
		return this.Id.compareTo(o.Id);
	}
	

}