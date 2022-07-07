package jundemos;

class Clone_Demo implements Cloneable
{
	private static Clone_Demo demo;
	int amount;
	private Clone_Demo()
	{
		System.out.println("Clone_Demo called....!");
	}
	public static Clone_Demo Object() 
	{
	if(demo==null)
	{
		demo=new Clone_Demo();
		return demo;
	}
	else
	{
		return demo.Clone();
	}

	}
	private Clone_Demo Clone()
	{
		try
		{
			return(Clone_Demo)super.clone();
		}catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}

