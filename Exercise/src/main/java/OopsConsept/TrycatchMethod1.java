package OopsConsept;

public class TrycatchMethod1
{
	void met() throws Exception {
		throw new ArithmeticException();
	}
	public static void main(String[] args)
	{
		try 
		{
			int i=1/0;
			//Integer.parseInt(s);
			
			System.out.println(i);
		} catch (Exception e) {
		//System.out.println(e);
		}
		finally
		{
			System.out.println("Exception is completed....!");
		}
	}

}

