package exercise;

public class MuthuDemo
{
	public static void main(String[] args)
	{
		abc s=new abc(0);
//s.p.a();
		s.meth5();
			
	}
}
class abc
{
	int  a=5;	
	static int b=6;
//construct types
	abc(int a)
	{
		a=this.a;
		System.out.println(this.a);
	}
	public abc(int a,int b)
	{
		System.out.println(b);
	}
	private abc()
	{
			
	}
		
		private abc meth2() {
			return new abc();
		}
		d f=new d();
		static class d{ 
//construct overloading
			d(){
				System.out.println("Hi");
			}
			d(int a) {
				System.out.println(a);
			}
			d(float a){
				System.out.println(a);
			}
			d(String str){
				System.out.println(str);
			}
		
		}
		void meth5() {
			c.a();
		}
//c p=new c();
		static class c{
			int a=5;
		static	int b=6;
			private c() {
				
			}
//method overloading
			static void a() {
				System.out.println(b);
				System.out.println("haiiii");
			}
			void a(int a) {
				
			}
			void a(int a,int i) {
				
			}
			void a(String s) {
				display();
			}
			void display() {
				
			}
		}
//static methods
		
	}