package demo;

import java.util.Scanner;

public class QuationRemainder {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int num=sc.nextInt();
		System.out.println("second no :");
		int num2=sc.nextInt();
		
		{
			double quation=num/num2;
			int remainder=num%num2;
			System.out.println(quation);
			System.out.println(remainder);
		}
		
	}

}
