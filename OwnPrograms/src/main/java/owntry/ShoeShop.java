package owntry;

public class ShoeShop{
	ShoeShop(){
		System.out.println("ShoeShop Created....!");
	}
	ShoeCompany s=new ShoeCompany();
	 SellsMan sell=new  SellsMan();
	
public void Print() {
	s.MakeShoes();
	s.Quality();
	s. ShoesCountInaDay();
	sell.ShoesRate();

}
	
   
	public void Models() 
	{		
		System.out.println("Shoes Models...:");
	}
	public void Brands()
	{
		System.out.println("BaTa");	
	}
	public void Warranty()
	{
		System.out.println("2years..");	
	}
	
	
	 
}
