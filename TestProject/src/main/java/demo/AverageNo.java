package demo;

import java.util.Scanner;

public class AverageNo {
public static void main(String[] args) {
int sum=0;
Scanner sc=new Scanner(System.in);
System.out.println("Enter the value rows :");
int ts= sc.nextInt();
System.out.println("ENTER THE VALUE");
int [] num=new int[ts];

for(int i=0;i<num.length;i++)
   {
	num[i]=sc.nextInt();
   }
   for(int m:num) {
  //int m=num;
	   sum=sum+m;
	   m++;
	  
   }
   int re=sum/num.length;
   System.out.println("total" +re);
}
}



