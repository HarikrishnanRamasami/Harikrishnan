package OopsConsept;

public class ComposistionDemo {
	public static void main(String[] args) {
	Ibaco icecream=new Choclate(new Vanila(new Strawberry()));
	
	System.out.println("Costvalue1..: "+icecream.Cost());
	
	Icecream vanila=new Vanila(new Strawberry());
	System.out.println("Vanila cost..: "+vanila.Cost());
	Icecream strawberry=new Strawberry();
	System.out.println("Strawberry Cost..: "+strawberry.Cost());
	Icecream choclate=new Choclate();
	System.out.println("Choclate ....cost..: "+choclate.Cost());
	
	}
}
abstract class Ibaco
{
	 abstract int Cost();
}
abstract class Icecream extends Ibaco{
	
}
class Vanila extends Icecream{
public Vanila() {
	// TODO Auto-generated constructor stub
}
	Ibaco ibaco;
	public Vanila(Ibaco ibaco) 
	{
		this.ibaco=ibaco;
	}@Override
	//Ibaco ibaco;
	public int Cost()
	{
		System.out.println(ibaco.Cost());
		if (ibaco==null) 
		{
		return 10;
	}
	else 
	{
		return 10+ibaco.Cost();
	}
	
	}
}

class Strawberry extends Icecream{
	public Strawberry() {
		// TODO Auto-generated constructor stub
	}
	Ibaco ibaco;
	public Strawberry(Ibaco ibaco) {
		this.ibaco=ibaco;
	}
	 int Cost() {
		 if (ibaco==null)
		 {
			 return 24;
		 }
		 else
		 {
			 return 24+ibaco.Cost();
		 }
		
	}
}
class Choclate extends Icecream{
	public Choclate() {
		// TODO Auto-generated constructor stub
	}
	Ibaco ibaco;
	public Choclate(Ibaco ibaco) {
		this.ibaco=ibaco;
	}
	public int Cost() {
		if(ibaco==null) {
			return 30;
		}
		else
		{
			return 30+ibaco.Cost();	
		}
	}
}