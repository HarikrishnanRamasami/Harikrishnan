package exercise;

public class Aadi {
	
		public static void main(String[] args)
		{
			
			
			House.saram="welcome";
			System.out.println(House.saram);
			
			/*House veedu1=new House();
			
			veedu1.saram="2 ton saram bought...";
			veedu1.toilet="western toilet...";
			System.out.println("Veedu1 saram...:"+House.saram);
			System.out.println("Veedu1 toilet..:"+veedu1.toilet);
			
			veedu1.toilet("gzdgzdf");*/
			
			House veedu2=new House();
			veedu2.toilet="indian toilet...";
			System.out.println("Veedu2 saram...:"+House.saram);
			System.out.println("Veedu2 toilet..:"+veedu2.toilet);
			veedu2.toilet("steel tap....");
			
			System.out.println("v1..:"+veedu2.toilet);
			System.out.println("v2..:"+veedu2.toilet);
			
			House.saram="3 ton saram....";
			
			System.out.println("v2...:"+House.saram);
		}
	}
		class House
		{
			static String saram;
			
			String toilet;
			 void toilet(String tap)
			{
				String taps=tap;
				System.out.println(taps);
				//System.out.println(""+House.saram);
			}
		}
		
		abstract class s1{
			 public abstract void dis();
			
		}
