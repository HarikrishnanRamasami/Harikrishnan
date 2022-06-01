package demo;

import java.util.Scanner;

//import java.util.Scanner;

public class TrueOrfalse {
	public static void main(String[] args) {
Scanner n=new Scanner(System.in);
		
		System.out.println("enter a character:");
		
		String sc=n.nextLine();
	  //char ch = n.nextLine();
	//	char [] a1= {'a','e','i','o','u'};
		String a1="adi";
		String a2="vijay";
		String a3="vicky";
		
			
		if( sc==a1|| sc==a2 || sc==a3)
				
		{
			System.out.println(" is vowel");
		}
		else
		{
			System.out.println(" is not vowel");
		}
	}

}
