package demo;

import java.util.Scanner;

public class ArrayInput 
{
public static void main(String[] args) 
   {
	int sum=0;
Scanner sc=new Scanner(System.in);
int []list=new int[10];
     for(int i=0;i<4;i++)
        {
           	list[i]=sc.nextInt();
        }
        for(int nt:list)
           { 
	         sum =sum+nt;
           }
           System.out.println(sum);
   }
}


		