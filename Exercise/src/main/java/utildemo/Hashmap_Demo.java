package utildemo;
/*
 * Hash Map print only shuffle The order of list
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Hashmap_Demo 
{
	public static void main(String[] args)
	{
		Map<String,Integer> hmap=new HashMap<String,Integer>();
		hmap.put("Ronaldo", 105);
		hmap.put("Messi",89);
		hmap.put("Jackie",207);
		hmap.put("Heathleger", 15);
		System.out.println(hmap);
				
		Set<Map.Entry<String,Integer>> set=hmap.entrySet();
		
		Iterator<Map.Entry<String,Integer>> iterator=set.iterator(); 
		
		for( Entry<String, Integer> entry : hmap.entrySet() ){
		    System.out.println( entry.getKey() + " => " + entry.getValue() );
		}
		
//		while(iterator.hasNext())
//		{
//			Map.Entry<String,Integer> alb=iterator.next();
//			System.out.println(alb.getKey()+" : "+alb.getValue());
//		}
//		
		hmap.put("Ronaldo",10);
		System.out.println(hmap.containsValue(105));
		System.out.println(hmap.equals(null));
		System.out.println(hmap.remove("Ronaldo"));
		System.out.println(hmap);
		System.out.println(hmap.replace("Dicaprio",105));
		System.out.println(hmap);
		
//		for(String i:hmap.keySet())
//		{
//			System.out.println("Key :"+i+"   "+"value :"+hmap.get(i));
//		}
		//System.out.println(hmap.hashCode()); //2105766870
		
		//System.out.println(hmap.toString());
		hmap.forEach(null);

	}
}


		


