package demo;

import java.util.Scanner;

public class DoWhiledemonstrate {
	public static void main(String[] args) {
		int rst=0;
	Scanner sc=new Scanner(System.in);
	int num=sc.nextInt();
	while(num<=10) {
		rst=rst+num;
		num++;
	}
		System.out.format("%d",rst);
	}
	}



