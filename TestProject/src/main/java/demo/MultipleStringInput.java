package demo;

import java.util.Scanner;

public class MultipleStringInput
   {
	public static void main(String[] args)
	  {
		Scanner demo=new Scanner(System.in);
		System.out.print("Enter how many names you want :");
		String[] str=new String[demo.nextInt()];
		demo.nextLine();
		//System.out.println("Enetr the values :");
		//for loop function
		
		for(int i=0;i<str.length;i++)
		   {
			str[i]=demo.nextLine();
		   }
		   System.out.println("\" Your names list :\"");
		   //creat each for loop
		   
		   for(String ech:str)
		     {
			   System.out.println(ech);
		     }
			   
	   }
}
   


