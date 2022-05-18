package demo;

import java.util.Scanner;

public class PrimeNumber {
	public static void main(String[] args) {
	//int temp;
	//i=2;
	
	boolean isPrime=true;
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter The value");
	int num=sc.nextInt();
/*	sc.close();
	for(int i=2;i<=num/2;i++) {
		 temp=num%i;
		 
		 if(temp==0) {
			 isPrime=false;
			 break;
		 }
	 }
	 if(isPrime)
		 System.out.println("its prime");
		 else 
		 
			 System.out.println("its not prime");*/
		//if (num==0 || num==1) {
			//System.out.println("is prime AND also nit prime");
		//}
		 if (num==0 || num==1 || num==2 || num==3 || num==5 || num==7){
			System.out.println("is prime");
		}
		else if (num%2!=0 && num%3!=0 && num%5!=0 && num%7!=0) {
			System.out.println("isnot prime");
		}
		else {
			System.out.println("not prime");
		}
     }
 }



