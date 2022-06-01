package OopsConsept;

public class InheritanceDemo5
{
	public static void main(String[] args) {
		
	
		VikramPlug vikramplug=new VikramPlug();
		 AmericanPlug americanplug=new LenevoPlug();
		 VikramSocket vikramsocket=new VikramSocket();
		 IndianAdaptar adapter=new IndianAdaptar(vikramplug,americanplug);
		 vikramsocket.roundPinHole(adapter, americanplug);
}
}

// abstract:1
abstract class IndianPlug
{
	public abstract void RoundPin();
}
//abstract:2
abstract class IndianSocket
{
	public abstract void roundPinHole(IndianPlug ip,AmericanPlug ap);
}
abstract class AmericanPlug
{
	public abstract void SlabPin();
}
//abstract:4
abstract class AmericanSocket
{
	public abstract void SlabPinHole(AmericanPlug ap);
}


class VikramPlug extends IndianPlug
{
	public void RoundPin()
	{
		System.out.println("Indian plug working....!");
	}
}
class VikramSocket extends IndianSocket
{

	public void roundPinHole(IndianPlug ip,AmericanPlug ap) {
		ip.RoundPin();
		
	}
}
class IndianAdaptar extends IndianPlug
{
	 AmericanPlug ap;
	 IndianPlug ip;
	public IndianAdaptar (IndianPlug ip, AmericanPlug ap) {
		this.ap=ap;
		this.ip=ip;
	}
	 
	public void RoundPin() {
	ap.SlabPin();
	
		
	}
}
class LenevoPlug extends AmericanPlug{
	@Override
	public void SlabPin() {
		
		System.out.println("American SlabPin IS working...!");
	}
	
}