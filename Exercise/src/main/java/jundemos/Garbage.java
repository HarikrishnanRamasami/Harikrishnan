package jundemos;

public class Garbage {
	public static void main(String[] args)
	{
		
	
	Runtime runtime= Runtime.getRuntime();
//	runtime.freeMemory();
//	runtime.gc();

	//runtime.addShutdownHook(null);
//	
//	runtime.exit(35);
	System.out.println(runtime.maxMemory());
	System.out.println(runtime.availableProcessors());
	Garbage_Demo garbage=new Garbage_Demo();
	garbage=null;
	runtime.gc();
	System.out.println(garbage.getGold());

	System.out.println(runtime.maxMemory());

	System.out.println(runtime.availableProcessors());
	}
}
class Garbage_Demo
{
	String name;
	
	private String gold="near by house...!";
	public Garbage_Demo()
	{
		for(int i=0;i<100;i++)
		{
			name=new String("Name is Arjundash...!"+i);
		}
	}
	protected String getGold()
	{
		return ("Gold is..!"+gold);
	}
	@Override
	protected void finalize() throws Throwable {
	System.out.println(getGold());
	}
}


