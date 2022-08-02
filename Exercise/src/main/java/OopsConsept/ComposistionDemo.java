package OopsConsept;

public class ComposistionDemo {
	public static void main(String[] args) {

		Ibaco vanila=new Vanila(new Choclate());
		 Icecream strawberry=new Strawberry();
		Icecream choclate=new Choclate();
		System.out.println("vanila...: "+vanila.Cost());
		System.out.println("strawberry...: "+strawberry.Cost());
		System.out.println("choclate...: "+choclate.Cost());
}
}
abstract class Ibaco
{
	 abstract int Cost();
}
abstract class Icecream extends Ibaco{
	
}
class Vanila extends Icecream {
	Ibaco ibaco;
	public Vanila() {
		
	}
	public Vanila(Ibaco ibaco) {
		this.ibaco=ibaco;
	}
	@Override
	int Cost() {
	if(ibaco==null)
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
	Ibaco ibaco;
	public Strawberry() {
	}
    Strawberry(Ibaco ibaco)
    {
    this.ibaco=ibaco;
    }
    int Cost() {
	if(ibaco==null)
	{
		return 20;
	}
    else
    {
    	return 20+ibaco.Cost();
    }
}
}

class Choclate extends Icecream{
	Ibaco ibaco;
	Choclate(){
		
	}
	Choclate(Ibaco ibaco){
		this.ibaco=ibaco;
	}
	@Override
	int Cost() {
	if(ibaco==null) {
	
		return 30;
	}
	else
	{
		return 30+ibaco.Cost();
	}
}
}
