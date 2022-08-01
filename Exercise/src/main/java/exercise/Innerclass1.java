package exercise;

public class Innerclass1{
	public static void main(String[] args) {
		OuterC.Inner cl=new OuterC. Inner();
		
		cl.InnerM();
	}
	
}

class OuterC
{
	int a=20;
	private static int b=30;
	public static void Methods()
	{
		
		System.out.println("Outer class methods...");
	}
	static class Inner{
	
		public void  InnerM() 
		{
			Methods();
			//int a;
			System.out.println("Inner class methods..."+b);
		}
	}
	
}

