package OopsConsept;

public class Ela {
	
	public static void main(String[] args) {
		Animals dd=new Lion();
		Animals ff= new Liger();
		ff.tiger1();
		dd.tiger1();
		dd.wilds();
	
	}
}
abstract class Animals{
	public Animals() {
		 System.out.println("It was  all  animal living in forest");
		
		
	}
	
	String name="They are wild animals ";
	final public void wilds() {
		System.out.println("many type animel there");
	}
	abstract void tiger1();
	
}
class Lion extends Animals{
	
	public void tiger1() {//this is overriding og the programming
	//	Fox gg=new Fox();
//		gg.fox("fox name is puby");
		System.out.println("it had four legs");
	}
}
	
class Liger extends Animals{
	public void tiger1(){
		System.out.println("ela");
	}
	public void fox() {
		System.out.println("the tiger is hunts deer");		
		
	}
	public void fox(String d) {
		System.out.println(d);
		System.out.println("it is also wild animals" );
	}
	


	
}
	



