package component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import servic.Myservice;

@Component("comp")
public class Mycomponent {
@Autowired
	private Myservice service;
	public Myservice getService() {
		return service;
	}
	public void setService(Myservice service) {
		this.service = service;
	}
	public void comp() {
		System.out.println("Mycomponent called....");
		service.service();
	}
}
