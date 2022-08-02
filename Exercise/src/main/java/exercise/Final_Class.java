package exercise;

public class Final_Class
{
public static void main(String[] args) {
	Tr tr=new Tr();
	tr.g();
}
}
final class Arjun
{
	final int age=20;
}
/*class Ft extends Arjun  // Final class canot be inherit
{
	
}*/

class Fire{
	final int age=20;
}
class Tr extends Fire{
	final int age=30;
	public void g(){
		
	System.out.println(super.age);
}
}

			/// Final Method can not be Overried

/*class Final_Overried{
	final void F_method() {
		
	}
}
class Extends extends Final_Overried
{
	final void F_method() {
		
	}
}*/