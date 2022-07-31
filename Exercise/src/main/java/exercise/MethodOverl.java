package exercise;

public class MethodOverl {
	public static void main(String[] args) {
		MethodOverl obj=new MethodOverl();
		
		obj.met(23.3f);
	}
	public void met(int i,int j) {
		System.out.println("int param met method called..");
	}
	
	 int met() {
		System.out.println("no param met method called...");
		return 1;
	}
	 void met(float f) {
		 System.out.println("float param method called...");
	 }
}