package exercise;

import java.util.Scanner;

public class PassMark 
     {
       public void TestMark()
            {
	          Scanner sc=new Scanner(System.in);
	          System.out.print("Enter the Name :");
	          int nm=sc.nextInt();
	 
	          if(nm<35)
	             {
		           System.out.println("Fail");
	             }
	          else if(nm==35 || nm<=60)
	             {
		           System.out.println("Pass");
	             }
	          else if(nm>=70 || nm<=80)
	             {
		           System.out.println("good");
	             }
             }
      }