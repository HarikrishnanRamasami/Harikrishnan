package demo;

import java.util.Scanner;

public class OddEven {
public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter the value");
	/*int no=sc.nextInt();
	if(no%2==0) {
		System.out.println(no+" "+"even");
	}
	else 
		System.out.println(no+" "+"odd"); */

	//ovel concept
	/*
	
	char ch=sc.next().charAt(0);
	
	if(ch=='i' || ch=='e' || ch=='f') {
		System.out.println("ovel");
	}
		else
			
			System.out.println("is not");
			*/
	
/*	int no=38,no2=5,no3=75;
	
	if(no>=no2 && no >=no3) {
		System.out.println(no+" "+"is largest no");
	}
	if(no2>=75) {
		System.out.println(no2+" "+" is the largest no");
	}
	else
	{
		System.out.println(no3+" "+"is the largest no");
		}*/
  //  for (int i=1; i <=4; i++) {

      /*  for(int i=1; i <= 3; i++)
        	{
        // spaces
        		for(int k=1; k <=5; k++)	
        			{
        				System.out.print("*"); // stars
        			}
        // new line
        			System.out.println();*/
	for(int i=0,j=2,k=1;i<=4&&j<=4||k<=4;i++,j++,k++) {
		System.out.println(" *");
	}
     System.out.println();  
     }
   
	}




