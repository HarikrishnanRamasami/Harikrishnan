package exercise;

public class Demo extends PassMark
      {
	    public static void main(String[] args)
	          {
		        Text ts=new Text();
		        Test name= new Test();
		       	Demo demo=new Demo();
		       	double at;
		       	name.Myvalue();
		        name.Myvalue2();
		        at=name.getValues(23);
		        System.out.println(at);
		        ts.Tiger();
		        name.LionSound(); 
	            name.Sleep();
		        demo.TestMark();
	          }
       }

class Text extends Animal
      {
	    public void Tiger()
	         {
		       System.out.println("National animal");
	         }
      }