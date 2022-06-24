package exercise;

public class Super_Keyword {
public static void main(String[] args) {

	//Super keyword methods
	/*Theatre theatre = new Theatre();
	theatre.Actor(22);
	theatre.Movies(127);
	theatre.Actor();*/
	
	
	
	/// Super class Variables object
	
	Print print=new Print();
	
	System.out.println(print.age);

}
}
///Method Reference Superclass ;

/*abstract class Top_Gun{
	final public void Actor(int age) {
		System.out.println("Tom cruise he is the americon actor...!"+" : "+ "age " + age);
	}
	public void Actor() {
		System.out.println("actor...top.!");
	}
}
abstract class Movie_List extends Top_Gun{
	final public void Movies(int List) {
		//super.Actor();
		System.out.println("List of movies...!"+List);
	}
	public void Actor() {
		System.out.println("supclass actor.2..!");
	}
}
class Theatre extends Movie_List{
	public void Actor() {
		super.Actor();
		System.out.println("Theatre..!");
	}
} */


///Super class Constructer AND variables refer

abstract class Dicaprio{
	int age=55;
}
abstract class Johnny_Depp extends Dicaprio {
	int age=48;
}
abstract class Downey extends Johnny_Depp{
	int age=57;
}

 class Print extends Downey{
	int age=40;
	public void Execute() {
		System.out.println(super.age);
		System.out.println(super.age);

}
}


