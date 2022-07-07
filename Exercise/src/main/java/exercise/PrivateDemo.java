package exercise;

public class PrivateDemo {
	public static void main(String[] args) {
		School.buildSchool();
		School.buildSchool();
	}
}
class School{
	private School() {
		System.out.println("School object....");
	}
	static int count=0;
	static School school=new School();
	
	public static School buildSchool(){
		if(count<5) {
			count++;
			return new School();
		}
		else
		{
			return school;
		}
		
	}
	
}

