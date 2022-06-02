package interfaceclass;

public class FunctionalInterface {
	public static void main(String[] args) {
		
//..........................................................................................................//
		
		//PART 1:
		//1.Anonymous Interface
		
		int value;
		value=new FIntertwo() {
			
			@Override
			public int met() 
			{
				return 100;
			}
		}.met();
		System.out.println("Finterfacetwo is working...!"+value);
		
		// 2.Method Interface
		
		FIntertwo two=new FunctionalInterface()::Mymethod2;
		value=two.met();
		System.out.println("Value of FIntertwo..."+value);
		
		//3.Lamda interface
		
		FIntertwo ftwo=()->{return 1000;};
		value=ftwo.met();
		System.out.println("Lamda interface Fintertwo...."+value);
		
//........................................................................................................//
		
		// PART 3
		
		//1.Anonymouse interface
		
		String values ;
		
		values=new FInterthree()
		{		
			@Override
			public String met(int i, String s)
			{			
				return i+":"+s;
			}
		}.met(22,"Nancy");
		System.out.println("FInterthree....."+value);
			
		
		//2.Rferenc3e Methods
		FInterthree three=new FunctionalInterface()::Method3;
		values=three.met(30,"Ronaldo");
		System.out.println("Finterthre..vakue..: "+values);
		
		//3.Lamda Methods
		FInterthree threes=(int in,String str)->{return in+":"+str;};
		values=threes.met(40, "Dhoni");
		System.out.println("FInterthree values...is...!"+values);
		
//...........................................................................................................//
		
		//PART 4
		//1.Anonymous interface
		
		
		Account account=new Account();
		account.balance=1000;
		Account myact=new FInterfour() {
			
			@Override
			public Account transfer(Account act, int atm) {
				act.balance=(act.balance-atm);
				return act;
			}
		}.transfer(account, 600);
		System.out.println("Balance... "+myact.balance); 
	
		//2.method reference
		Account taccount=new Account();
		taccount.balance=1000;
		
		FInterfour four=new FunctionalInterface()::Method4;
		taccount =four.transfer(account, 300);
		System.out.println("pART 4 METHOD reference.... "+taccount.balance);
		
		
		// Lamda interface
		Account saccount=new Account();
		account.balance=1000;
		
		FInterfour fours=(act,atm)->{
		act.balance=(act.balance-atm);
				return act;
	};

	saccount=fours.transfer(account, 500);
	System.out.println("FIntfour LAmda.... "+saccount.balance);
	//.......................................................................................................//
	//PART 5
	//Anonymous 
/*	Shop sps=new Shop();
	sps.Shoe(30);
	Shop shop=new Shop() {
		
	}*/
	}

	
	public static void Mymethod()
	{
		System.out.println("My method output process...!");
	}
	public int Mymethod2()
	{
		return 1000;
	}
	public String Method3(int i,String s)
	{
		return i+":"+s;
	}
	public Account Method4(Account act,int atm) {
		act.balance=(act.balance-atm);
		return act;
		
	}
	public Shop method1(Shop sp) {
		sp.Shoe(30);
		return sp;
	
	}

	
}
interface FInterone{
	public int met();
}
interface FIntertwo{
	public int met();
}
interface FInterthree{
	public String met(int i,String s);
}
interface FInterfour{
	public Account transfer(Account act,int atm);
}

class Account {
	int balance;
}

interface Shops{
	public Shop cardaccess(Shop sp);
}

class Shop
{
  public void Shoe(int a) {
	  
	  a=12;
  }
}

