package OopsConsept;

public class Inheritance
{
	public static void main(String[] args)
	{
		SuperClass sub= new SubClass();
		System.out.println(sub.name +" : "+sub.age);
		sub.Method(37," abd williers");
	
		sub.NameAge();
		
	}
}
abstract class SuperClass
{
	
	public SuperClass()
	{
	System.out.println("Print NameList...!");
	}
	final String name="Haridcruz";
	final int age=22;
	public void Method(int abd,String s) {
		System.out.println(abd +" : "+s);
	}
	abstract public void NameAge();
}


class SubClass extends SuperClass
{
	public void NameAge() 
	{
		System.out.println("Name list Printed....");
	}
}
