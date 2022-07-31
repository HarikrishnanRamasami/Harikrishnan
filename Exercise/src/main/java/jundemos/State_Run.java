package jundemos;

public class State_Run 
{
	public static void main(String[] args)
	{
//		met(State.TAMILNADU);
//	}
//	public static void met(State st)
//	{
//		System.out.println(st.details);
//	}
	for(State m:State.values())
	{
		System.out.println(m.details);
	}
}
}
