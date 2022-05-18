package demo;

public class PrimeNo {
public static void main(String[] args) {
	 
for(int i=0;i<=100;++i) {
	if(i==0 || i==2 || i==3 || i==5 || i==7)
	{
		System.out.print(i+" ");
	}
	else if(i%2!=0 && i%3!=0 && i%5!=0 && i%7!=0) {
	System.out.print(i+" ");
		}
}
}
}


