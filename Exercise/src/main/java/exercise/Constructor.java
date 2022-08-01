package exercise;

public class Constructor {
	static{
		System.out.println("Static method is the keywords...");
	}

	public static void main(String[] args) {
		ThisDemo demo= new ThisDemo(20,40);
		demo.Methods("gasvfevfc","aaaa");
		
		
}
}

class ThisDemo{
	
	int s;
	int f;
	int a;
	 ThisDemo(int s,int f){
		 this.s=s;
		 this.f=f;
		a=6;
		 
	}
	void Methods(String s,String f) {
		System.out.println(this.f);
		//a=5;
		//int c=a+b;
		System.out.println(a);
		//System.out.println(b);
	}
}
class Deamon{
	
}