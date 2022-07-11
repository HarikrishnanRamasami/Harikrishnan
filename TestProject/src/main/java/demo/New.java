package demo;
public class New{
	public static void main(String[] args) {
		int i;
		int j;
		outer:
		for (i=1;i <3;i++)//1,2
		inner:
		for(j=1; j<3; j++) {//1,2=1&2=1,2
		if (j==2)
			
		continue outer;
		System.out.println(j);
		System.out.println("Value for i=" + i + " Value for j=" +j);
		}
		
	}
}    

	
	     

	