package exercise;

public class ClassVariable {
	public static void main(String[] args) {
		ClassRoom dhoni= new ClassRoom();
		ClassRoom ronaldo= new ClassRoom();
		
		System.out.printf(dhoni.projecter+":"+ dhoni.toilet);
		System.out.println(ronaldo.projecter +":"+ ronaldo.toilet);
	}

}

class ClassRoom{
	Projecter projecter=new Projecter();
	static Toilet toilet=new Toilet();
}
class Projecter{
	
}
class Toilet{
	
}