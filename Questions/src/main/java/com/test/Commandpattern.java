package com.test;

public class Commandpattern {
	public static void main(String[] args) {
		UniverseRemote.remote();
	}

}
class UniverseRemote{
	public static void remote() {
		Tv tv=new Tv();
		SeTopBox ST=new SeTopBox();
		Netflix nf=new Netflix();
		Lowsound lw=new Lowsound();
		
		FatherChannel fc=new FatherChannel(tv, ST, nf, lw);
		MotherChannel mc=new MotherChannel(tv, ST, nf, lw);
		
		Remote rt=new Remote();
		rt.setcommand(mc, 0);
		rt.setcommand(fc,1);
		rt.execute(0);
		
	}
}
class Tv{
	public void Switchon() {
		System.out.println("Switch on....");
	}
	public void Av1() {
		System.out.println("Tv to Av1 Mode On...");
	}
}
class SeTopBox{
	public void SerielChannel() {
		System.out.println("Seriel channel on...");
	}
	public void Newschannel() {
		System.out.println("news Channel on...");
	}
}
class Netflix{
	public void Avengers() {
		System.out.println("Avengers..movie Started.....");
	}
}
class Lowsound{
	
	public void Sound() {
		System.out.println("sound valuem decreased....");
	}
}

interface Command{
	public void execute();
}

class FatherChannel implements Command{
		Tv tv;
		SeTopBox setopbox;
		Netflix netflix;
		Lowsound low;
	public FatherChannel(Tv tv,SeTopBox setopbox,Netflix netflix,Lowsound low) {
		
		this.tv=tv;
		this.setopbox=setopbox;
		this.netflix=netflix;
		this.low=low;
	
	}
	@Override
	public void execute() {
	
		tv.Switchon();
		tv.Av1();
		setopbox.Newschannel();
		low.Sound();
		
	}
}

class MotherChannel implements Command{
	
	Tv tv;
	SeTopBox setopbox;
	Netflix netflix;
	Lowsound low;
	public MotherChannel(Tv tv,SeTopBox setopbox,Netflix netflix,Lowsound low) {
		this.tv=tv;
		this.setopbox=setopbox;
		this.netflix=netflix;
		this.low=low;
	}
	@Override
	public void execute() {
		
		tv.Switchon();
		tv.Av1();
		setopbox.SerielChannel();
		netflix.Avengers();
		low.Sound();
	}
}
class Dummy implements Command{
	@Override
	public void execute() {
		
		System.out.println("im dummy.....");
	}
}
class Remote{
	
	Command[] command=new Command[5];
	
	public Remote() {
		for (int i=0;i<command.length;i++) {
			command[i]=new Dummy();
		}
	}
	public void setcommand(Command command,int slot) {
		
		this.command[slot]=command;
		
	}
	public void execute(int slot) {
		command[slot].execute();
	}
}