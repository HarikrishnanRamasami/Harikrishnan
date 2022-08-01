package jundemos;

public class Generics_Demo
{
	public static void main(String[] args) 
	{
		Good_Paint_Brush<Water> gpb=new Good_Paint_Brush<>();
		Water water=new Water();
		Air air=new Air();
		//gpb.SetColor(water);
	//	gpb.getColor();
		
	//	water=(Water) gpb.getColor();
		water.Sprinkle();
	}
}

class Water
{
	public void Sprinkle()
	{
		System.out.println("Water Sprinkle....!");
	}
}

class Air
{
	public void Dusting()
	{
		System.out.println("Dusting....!");
	}
}



class Good_Paint_Brush<Brush>
{
	private Brush paint;
	public Brush getColor()
	{
		return this.paint;
	}
	public void SetColor(Brush Paint)
	{
		this.paint=paint;
	}
}