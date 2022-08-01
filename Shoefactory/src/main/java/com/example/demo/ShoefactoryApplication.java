package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ShoefactoryApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext cac=	SpringApplication.run(ShoefactoryApplication.class, args);
		
		ShoeShop shop=cac.getBean("cr7",ShoeShop.class);
		
		System.out.println(shop.sellshoe());

	}
}

abstract class Shoe{
	
}

abstract class Factory{
	
	public abstract Shoe makeshoe();

}

class Lathershoe extends Shoe{}

class SportsShoe extends Shoe{}

@Component("bsf")
class BtatashoeFactory extends Factory{
	
	public BtatashoeFactory() {

		System.out.println("btata object.....");
		
	}
	
	@Override
	public Shoe makeshoe() {
	
		return new Lathershoe(); 
	
	}
}
@Component("lsf")
class LakhaniShoeFactory extends Factory{
	
	public LakhaniShoeFactory() {
		
		System.out.println("LakhaniShoe object....");
	}
	
	@Override
	public Shoe makeshoe() {
	
		return new SportsShoe();
	}
}

abstract class ShoeShop{
	@Autowired
	@Qualifier("bsf")
	private Factory factory;

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	
	public abstract Shoe sellshoe();
	
}
@Component("cr7")
class Cr7 extends ShoeShop{
	
	public Cr7() {
		
		System.out.println("cr7 object......");
		
	}
	 @Override
	public Shoe sellshoe() {
		 System.out.println(getFactory());
		return getFactory().makeshoe();
	}
}