package exercise;

public class Calculator 
     {	
	    String atr="Only using inside the method....";
    static void Add(int a,int b) 
      	 {
    	  if(a>=b)
    	   {
    	    int values=a+b;
    	    System.out.println(values);
    	   }
      	 }
    
       String Get(int a,int b)
          {
    	   String str=atr;
    	   return str;
    	 //  return 100;
          }
       void Dis(int r,int d)
          {
    	   display(r/d);
          }
       void display(int s)
          {
		System.out.println("The result is..:"+s);
          }
       void Result(int r[])
          {
    	   int result=0;
    	   for(int s:r)
    	      {
    		   result=result+s;
    		   System.out.println( result);
    	      }
          }
       }
         
    
