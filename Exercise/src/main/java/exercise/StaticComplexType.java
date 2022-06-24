package exercise;

public class StaticComplexType {
	public static void main(String[] args) {
		ClassRoom1 newton=new ClassRoom1();
		
		ClassRoom1 edison=new ClassRoom1();
		
		System.out.println(newton.sanyo+":"+newton.sulab);
		
		System.out.println(edison.sanyo+":"+edison.sulab);
		
		ClassRoom1 gandhi=new ClassRoom1();
		System.out.println(gandhi.sanyo+":"+gandhi.sulab);
	}
}
class ClassRoom1{
	Projector sanyo=new Projector();
	static ToiletDemo sulab=new ToiletDemo();	
}
class Projector{
	
}
class ToiletDemo{
	
}
class Human{}


