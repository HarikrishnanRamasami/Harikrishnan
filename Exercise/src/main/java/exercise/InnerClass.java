package exercise;

public class InnerClass
{
	public static void main(String[] args)
	{
	PepsiCompany pepsi=new PepsiCompany();
	pepsi.Pepsi();
	
	}
}

class India{
	public void TamilNadu() {
		System.out.println("produce water.....!");
	}
	class Water{
		public void Waters()
		{
			System.out.println("Make water....!");
		}
		class KalimarkCompany
		{
			public void Kalimark()
			{
				CampaCola cc=new CampaCola();
				cc.Cola();
				System.out.println("Kalimark fill and sell the bovanto...");
			}
			class CampaCola
			{
				public void Cola() 
				{
					System.out.println("CampaCola make cola...!"); 
				}
			}
		}
	}
}

class PepsiCompany
{
	public void Pepsi()
	{
		//India.Water.KalimarkCompany.CampaCola cola=new India().new Water(). new KalimarkCompany().new CampaCola();
				
		System.out.println("Pepsi Company fill and sell the pepsi...");
		//India aa=new India();
		//India.Water ts=new India().new Water();
		//India.Water.KalimarkCompany tt =new India().new Water().new KalimarkCompany();
		//aa.TamilNadu();
		
		
		
	}
}

