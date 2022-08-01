package owntry;

//Interface Using;

interface ShoeManifacture{
	public void MakeShoes();
	public void Quality();
	public void ShoesCountInaDay();
}

//Implement interface;

public class ShoeCompany implements ShoeManifacture
{
	ShoeCompany (){
		System.out.println("ShoeCompany Builded...!");
	}
	String s="Puma";
	public void MakeShoes() {
		System.out.println("Shoes Make Method.....");
	}
	public void Quality() {
		System.out.println("Using Camel lethar....");
	}
	public void  ShoesCountInaDay() {
		int a=3000;
		System.out.println("Per day...:"+a+"Shoes Manifactured.");
		
	}
	String Get() {
		return s; 
	}

}



