package wrapper;

public class Ternary {

	public static void main(String[] args) {
		//Ternary Operator
		
		String name=(50<52)? "Yes fifty is lessthen fifty two":"No fifty is not lessthen fifty two...";
		System.out.println("50<52:");
		System.out.println(name);
		System.out.println("59==52:");
		
		System.out.println((59==52)? "Yes 59 equals to 52..." : "No is not equals to 52");
		System.out.println("If condition");
		if (22%2!=0){
			System.out.println("yes");
		}
		else {
			System.out.println("No");
		}
		System.out.println("Names:");
		String vip="Dhoni";
		
		if(vip.equals("Cristiano ronaldo")) {
			System.out.println("He is a football player");
		}
		else if(vip.equals("Dhoni")) {
			System.out.println("He is a Cricketer");
		}
		else if(vip.equals("Cillian")) {
			System.out.println("He is american actor");
		}
		else {
			System.out.println("I don't know");
		}
	}
}

