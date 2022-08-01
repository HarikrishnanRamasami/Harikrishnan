package wrapper;

import java.util.Scanner;

public class ScannerClass {
public static void main(String[] args) {
	Scanner sr=new Scanner(System.in);
	
	System.out.println("Enter your name:");
	String name=sr.next();
	System.out.println("Welcome to qic mr.. "+name);

	System.out.println("Enter your age:");
	int age=sr.nextInt();
	System.out.println("Your age is..:"+age);
}
}
