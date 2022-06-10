package jundemos;

public class Clone_Demo_Ad {
	public static void main(String[] args) {
		School har=new School(1) ;
		//har.met();
		
		School a=har.s();
		
		a.met();
		
		har.c=21.4f;
		
		a.c=6.5f;
		
		System.out.println(har.c);
		
		System.out.println(a.c);
		
	}
}
class School implements Cloneable{
	float c=21.5f;
	public School(int i) {
		System.out.println("1 Standard Books are Provided.............."+i);
	}
	public School(long b) {
		System.out.println("2 Standard Books are Provided.............."+b);
	}
	public School(float c) {
		this.c=c;
		System.out.println("3 Standard Books are Provided.............."+this.c);
	}
	public School s()  {
		try {
		return (School)super.clone();
		}catch(Exception e) {
			return null;
		}	
	}
	void met() {
		System.out.println("Hi.............................");
	}
}