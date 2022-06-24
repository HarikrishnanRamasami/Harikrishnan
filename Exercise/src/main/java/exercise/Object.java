package exercise;

/*public class Object {
public static void main(String[] args) {

	Test name= new Test();
	double at;
	name.Myvalue();
    name.Myvalue2();
    at=name.getValues(23);
System.out.println(at);
    name.LionSound();
    name.Sleep();
}
}*/

 class Test implements Sample
      {
	    int i=10;
	    public void LionSound() 
	         {
		       System.out.println("gg");
	         }
	    public void Sleep()
	         {
		       System.out.println("zzzz");
	         }
	    public void Myvalue()
	         {
		       int j=20;
	       	   System.out.println(i + j);
	         }
	    public void Myvalue2() 
	         {
		       System.out.println(i);
	         }
	    double getValues(int atm)
	         {
		       return 5+atm*5;
	         }
       }

 
interface Sample
      {
        public void LionSound();
	    public void Sleep();
      }
   
  
abstract class Animal 
      {
        public abstract void Tiger();
        public void Cheeta()
             {
    		   System.out.println("Speed");
    	     }
      }