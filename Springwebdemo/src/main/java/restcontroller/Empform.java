package restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@XmlRootElement

class Employee {

	private int empid;

	private String empname;

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}
}

@Repository

class EmployeeRepo {

	public Employee retrieve(int eid) {

		Employee emp = new Employee();

		emp.setEmpid(eid);

		emp.setEmpname("Cristiano");

		return emp;
	}

	public List<Employee> getAll() {

		Employee emp = new Employee();

		emp.setEmpid(100);

		emp.setEmpname("Dhoni");

		Employee emp1 = new Employee();

		emp1.setEmpid(200);

		emp1.setEmpname("Sachin");

		List<Employee> list = new ArrayList<>();

		list.add(emp1);

		list.add(emp);

		return list;
	}

	public void store(Employee e) {

		System.out.println("employee stored....");
	}

	public Employee search(String name) {

		Employee emp = new Employee();

		emp.setEmpid(100);

		emp.setEmpname("Dhoni");

		return emp;
	}

	public Employee delete(int eid) {
		
		System.out.println("deleted.....!");
		
		return new Employee();
		
	}

}

@RestController

public class Empform {
	
	@Autowired
	
	EmployeeRepo repository;
	
	@GetMapping("/rest/employe/get/{id}")
	
	public Employee getEmployeeById(@PathVariable("id")int id) {
		
		return repository.retrieve(id);
		
	}
	
	@GetMapping("/rest/employee/getAll")
	
	public List<Employee>getAll(){
		
		return repository.getAll();
	}
	
	@PostMapping("/rest/employee/create")
	
	public Employee creatEmployee(@RequestBody Employee emp) {
		
		repository.store(emp);
		
		return emp;
		
	}
	
	@GetMapping("/rest/employee/search/{name}")
	
	public Employee getEmployeeByName(@PathVariable("name") String name) {
		
		return repository.search(name);

	}
	
	@DeleteMapping("/rest/employee/delete/{id}")
	
	public Employee deleteEmployee(@PathVariable("id")int id) {
		
		return repository.delete(id);
			
	}

}
