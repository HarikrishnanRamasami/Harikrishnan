package OopsConsept;

import java.util.Scanner;

public class TryCatchMethod
{
public static void main(String[] args)
	{
//	try {int i=1/0;
//	System.out.println(i);
//		
//	} catch (Exception e) {
//		System.out.println("dont give the 0");
//	}
//	}
//}
	try 
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the first value...: ");
		int first=scanner.nextInt();
		System.out.println("Enter the Second Value...: ");
		int second=scanner.nextInt();
		
		int Result=first/second;
		
	} catch (ArithmeticException e) 
	{
		System.out.println(e);
		new Handler().Handle(e);
	}
	}
}
class Handler{
	public void Handle(ArithmeticException at) {
		System.out.println("Dont put The zero Value in second number...!"); 		
	}
}   

