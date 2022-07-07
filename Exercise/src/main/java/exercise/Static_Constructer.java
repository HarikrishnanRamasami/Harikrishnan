package exercise;

class Static_Constructer {
	public static void main(String[] args) {
		De demo=new De();
		demo.m1(32);
	}
	
 static void met() {
		
	}
 static void met(int i) {	 
 }
}
//...........................................................
class Static{
	public static void m1(int i) {
		System.out.println("good...!"+i);
	}
}

class De extends Static {
	
	public static void m1(int i) {
		
		System.out.println("hello...!"+i);
	}
}






