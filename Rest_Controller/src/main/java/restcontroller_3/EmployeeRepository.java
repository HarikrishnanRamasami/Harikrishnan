package restcontroller_3;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
	public List<Employee> findAll() {
		Employee emp = new Employee(1, "ramu", 2000);
		Employee emp2 = new Employee(2, "somu", 3000);
		ArrayList<Employee> al = new ArrayList<>();
		al.add(emp);
		al.add(emp2);
		return al;
	}

	public Employee save(Employee emp) {
		return emp;
	}

	public Employee findById(long id) {
		Employee emp = new Employee(1, "ramu the java model", 4000);
		return emp;
	}

	public void deleteById(long id) {
		System.out.println("emp deleted....");
	}
}
