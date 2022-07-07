package wrapper;

import java.util.Scanner;

public class ArithmeticOperator {

	public static void main(String[] args) {
		Scanner num1= new Scanner(System.in);
		System.out.println("Enter the number:");
		int no = num1.nextInt();
		System.out.println("Enter the second :");
		int no2=num1.nextInt();
		int result=no+no2;
		int se=no-no2;
		int th=no*no2;
		
		System.out.println(result);
		
		System.out.println(no==no2 || no!=no2);
		System.out.println(no==no2 && no<=no2);
		System.out.println(!(no==no2 || no>=no2));
		System.out.println(no!=no2);
		System.out.println(no>no2);
		System.out.println(no<no2);
		System.out.println(result>se || se<th);


	}

}
