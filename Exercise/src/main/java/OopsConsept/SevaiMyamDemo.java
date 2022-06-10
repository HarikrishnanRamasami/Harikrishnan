package OopsConsept;

public class SevaiMyamDemo 
{
	public static void main(String[] args) 
	{
		
	}

}
final class Helth
{
	public void dopostmortem() 
	{
		System.out.println("Post mortem...process");
	}
}
final class Police
{
	public void doinvestication() 
	{
		System.out.println("i am still investication...");
	}
}
final class Corporation
{
	public void cerificat()
	{
		System.out.println("cerdificate given...");
	}
}
interface Command
{
	public void execute();
}
class SevaiMayam
{
	public SevaiMayam()
	{
		Command[] command=new Command[5];
		for (int i=0;i<command.length;i++)
		{
			command[i]=new DummyCommand();
		}
	}
		public void SetCommand(Command command,int slot)
		{
			
//			command[slot]=command;
		}
		public void executecommand()
		{
//			command[slot].execute();
		}
	}

class DummyCommand implements Command
{
	public void execute()
	{
		System.out.println("I Am dummy yet investication...!");
	}
}
class DeathCertificates implements Command
{
	 Helth helth;
	 Police police;
	 Corporation corporation;
	 public DeathCertificates(Helth helth,Police police,Corporation corporation) 
	 {
		this.helth=helth;
		this.police=police;
		this.corporation=corporation;
	}
	 public void execute() {
		 System.out.println("death cerdificate process started...!");
	 }
}