package demo;

import java.util.Scanner;

public class NestedSwitch {
public static void main(String[] args) {
	Scanner scanner=new Scanner(System.in);
	System.out.println("Enter the value:");
	
	int sc=scanner.nextInt();
	System.out.println("Enter the name:");
	
	String st=scanner.next();
    //scanner.close();
	

	switch (sc) {
	case 1:
		System.out.println("don't quit");
		break;
	case 2:
		System.out.println("first learn then remove \"L\"");
		//break;
	case 3:
		
	   // scanner.close();
		switch(st) {
		
		case "cristiano":
			System.out.println("talent withouth hard work is nothing");
			break;
		case "dhoni":
			System.out.println("Don't think about the result,becase result puts presur on us");
			break;

		
		}
	}
}
}



