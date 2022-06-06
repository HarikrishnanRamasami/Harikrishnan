package exercise;

public class DefaultValuesPrimitive
     {
	   static int i;
     //static	char c;
       static	float f;
       static	double d;
       static	String s;
       static	short sh;
       static	boolean b;
       
       public static void main(String[] args)
            {
              byte a=20;
              int a1=92;
              a=(byte)a1;
              char c='a';
              c=(char)a1;
	         
              System.out.println(i);
	          System.out.println(c);
	          System.out.println(f);
	          System.out.println(d);
	          System.out.println(s);
	          System.out.println(sh);
	          System.out.println(b);
	        }
      }