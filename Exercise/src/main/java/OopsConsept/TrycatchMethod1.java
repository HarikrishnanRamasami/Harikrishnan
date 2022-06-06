package OopsConsept;

public class TrycatchMethod1
{
	public static void main(String[] args)
	{
		try 
		{
			String s=args[0];
			//Integer.parseInt(s);
			
			System.out.println(s);
		} catch (Exception e) {
		System.out.println(e);
		}
		finally
		{
			System.out.println("Exception is completed....!");
		}
	}

}

