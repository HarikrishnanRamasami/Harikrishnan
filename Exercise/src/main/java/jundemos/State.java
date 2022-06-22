package jundemos;

public enum State 
{
	TAMILNADU("salem,chennai,tharmapuri...!"), ANDHRA,KARNADAKA, KERALA;

	String details;
	private State()
	{
		
	}
	private State(String details)
	{
		this.details=details;
	}
}
