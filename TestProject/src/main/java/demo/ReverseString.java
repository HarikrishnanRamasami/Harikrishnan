package demo;

import java.util.Scanner;
import java.lang.StringBuffer; 
public class ReverseString {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Name :");
		String str=sc.nextLine();
		 StringBuffer sb=new StringBuffer(str);
		 sb.reverse();
		 {
			System.out.println("Reverse value :"+"\n"+sb); 
		 }
	}
}


