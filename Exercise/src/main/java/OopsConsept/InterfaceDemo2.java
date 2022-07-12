package OopsConsept;

public class InterfaceDemo2
{
	public static void main(String[] args)
	{
		CorporationIDCard corporatstudens =new CorporationStudens();
		ConventIDCard conventstudens=new ConventStudens();
		
		InterfaceDemo2 interfacedemo=new InterfaceDemo2();
		interfacedemo.LuxuryRoom(corporatstudens);
		interfacedemo.ordinaryRooms(conventstudens);
		interfacedemo.mess(corporatstudens);
	}
	public void LuxuryRoom(CorporationIDCard cid)
	{
		
	}
	public void ordinaryRooms(ConventIDCard cvic)
	{
	}
	public void mess(CorporationIDCard cid)
	{
		
	}

}

// Interface class

interface CorporationIDCard
{
	public void M1();
}
interface ConventIDCard
{
	public void M2();
}

// Extands the KeyWords

class CorporationStudens implements CorporationIDCard,ConventIDCard
{
	public void M1()
	{
		System.out.println("CorporationStudence print..!");
	}
	public void M2()
	{
		System.out.println("Corporation 2...success");
	}
}
class ConventStudens implements ConventIDCard
{
	public void M2()
	{
		System.out.println("ConventStudens Prints...!");
	}
}

