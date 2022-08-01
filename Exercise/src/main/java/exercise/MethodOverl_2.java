package exercise;

public class MethodOverl_2 {
	public static void main(String[] args) {
		Dog tiger=new Dog();
		tiger.play(new Biscuit());
	}
	
}
class Dog{
	public void play(Biscuit b) {
		System.out.println("nai valattum........");
	}
	public void play(Stick s) {
		System.out.println("nai odip pogum....");
	}
	
	public void play(Stone s) {
		System.out.println("nai kadikkum.....");
	}
}
class Biscuit{}
class Stick{}
class Stone{}