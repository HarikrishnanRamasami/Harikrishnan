package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import component.Mycomponent;
import servic.Myservice;



@SpringBootApplication
@ComponentScan(basePackages = {"component","servic","repdao"})
public class SpringdemoApplication {

	public static void main(String[] args) {
	ConfigurableApplicationContext	cxt=SpringApplication.run(SpringdemoApplication.class, args);
	
	Mycomponent srv=cxt.getBean("comp",Mycomponent.class);
	srv.comp();

	}

}
