package jundemos;

import java.util.Stack;

public class Stack_Demo {
	public static void main(String[] args)
	{
		
		Stack<String> sk=new Stack<>();
		boolean b=sk.isEmpty();
		System.out.println(b);
		sk.push("Dicaprio");
		sk.push("Johny Depp");
		sk.push("Heath ledger");
		b=sk.isEmpty();
		System.out.println("Values"+sk+":"+b);
		System.out.println(sk.capacity());
		
		
	}

}
