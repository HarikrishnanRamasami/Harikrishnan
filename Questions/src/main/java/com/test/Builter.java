package com.test;

public class Builter {
	public static void main(String[] args) {
		Bike bi=new Bike.BuildBike("120",400000).getcc(true).getpetrol(true).crate();
		System.out.println(bi.getcc(true));
		System.out.println(bi.getpetrol(true));
}
}
class Bike{
	private String speed;
	private int price;
	private boolean cc;
	private boolean petrol;
	
	public String getspeed(String speed) {
		return  speed;
	}
	public int getprice(int price) {
		return price;
	}
	public boolean getcc(boolean cc) {
		return cc;
	}
	public boolean getpetrol(boolean petrol) {
		return petrol;
	}
	
	private Bike(BuildBike bike) {
		
		this.speed=bike.speed;
		this.price=bike.price;
		this.cc=bike.cc;
		this.petrol=bike.petrol;
		
	}
	public static class BuildBike{
		
		private String speed;
		private int price;
		private boolean cc;
		private boolean petrol;
		
		public BuildBike(String speed ,int price) {
			this.speed=speed;
			this.price=price;
		}
		public BuildBike getcc(boolean cc) {
			this.cc=cc;
			return this;
		}
		public BuildBike getpetrol(boolean petrol) {
			this.petrol=petrol;
			
			return this;
		}
		public Bike crate() {
			return new Bike(this);
		}
	}
}