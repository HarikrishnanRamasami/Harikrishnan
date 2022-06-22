package jundemos;

public class Clone_Demo2_Main {
public static void main(String[] args) {
	Clone_Demo clone= Clone_Demo.Object();
	Clone_Demo clone1= Clone_Demo.Object();
	clone.amount=2000;
	clone1.amount=1000;
	
	System.out.println(clone.amount);
	System.out.println(clone1.amount);
}
}
