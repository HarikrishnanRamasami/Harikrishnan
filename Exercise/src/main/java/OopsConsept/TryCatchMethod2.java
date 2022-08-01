package OopsConsept;

public class TryCatchMethod2
{
public static void main(String[] args) 
	{
	if(args.length==0)
	{
		System.out.println("please give a argumenrt value..!");
	}
	else
	{
		String v=args[0];
		char c=v.charAt(0);
		if(Character.isDigit(c))
		{
			int n=Integer.parseInt(c+"");
			if(n==0)
			{
				System.out.println("Please give non zero value...!");
			}
			else
			{
				System.out.println(n);
				int x=1/n;
			}
			
			}
		}
	}
}
