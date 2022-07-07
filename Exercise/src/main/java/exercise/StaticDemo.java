package exercise;

public class StaticDemo {
	public static void main(String[] args) {
		Demostatic.Greed();
		Demos.Status(20);
		Tom tom=new Tom();
		tom.Jerry(20,30);
		Tom.Cat("crt",45);
		
	}
}

class Demostatic{
	public static void Greed()
	{
		System.out.printf("Have a good day....?");
	}
}
 class Demos{
	 static void Status(int a) {
		 System.out.printf("\nyes i don't expected about this happens"+"\t"+a);
		 System.out.println("Enjoyment....?");
	 }
 }
 
 class Tom{
	  void Jerry(int a,int b) {
		 int in=a+b;
		 System.out.println(in);
		 Cat("crj",105);
		// System.out.println();
	 }
	 static void Cat(String a,double b) {
		 System.out.println(a+":"+b);
		 String v=String.format("Numbers...:%d \n Lts...:%s", 22,"ronaldo");
		 System.out.println(v);
	 }
 }
 
 