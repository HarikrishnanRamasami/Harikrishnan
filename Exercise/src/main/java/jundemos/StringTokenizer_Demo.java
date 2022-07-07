package jundemos;

import java.util.StringTokenizer;

public class StringTokenizer_Demo
{
	public static void main(String[] args) 
	{
		StringTokenizer str=new StringTokenizer("Cristiani Is he BesT Player");
		int Count=str.countTokens();
		
		for(int i=0;i<Count;i++)
		{
			System.out.println("Cout...["+i+"] :"+str.nextToken());
		}
//		while(str.hasMoreTokens())
//		{
//		System.out.println(str.nextToken());
//		}
	}
}
