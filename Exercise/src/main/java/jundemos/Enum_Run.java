package jundemos;

import java.util.Scanner;

class Enum_Run
{
	Days day;
	
	public Enum_Run(Days day)
	{
		this.day=day;
	}
	
	
	public void DayLike()
	{
		
	
	
	switch (day)
	{
	case MONDAY:
		
		System.out.println("MONDAY IS WORKING DAY....!");
		break;
	case SUNDAY:
		System.out.println("SUNDAY IS HOLLYDAY...!");
		break;
	}
	}
	public static void main(String[] args) {
		Scanner scanner =new Scanner(System.in);
		String str=scanner.nextLine();
		
		Enum_Run TS=new Enum_Run(Days.valueOf(str));
		TS.DayLike();
		
	}
}