package restcontroller_3;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyRestController {
	
	private final EmployeeRepository repository = new EmployeeRepository();

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	@PostMapping("/employees")
	public Employee newEmployee(@RequestBody Employee newEmployee) {
		System.out.println("new employee saved..........");
		return repository.save(newEmployee);
	}

	// Single item
	@GetMapping("/employees/{id}")
	public Employee getEmp(@PathVariable Long id) {
		return repository.findById(id);// .orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return repository.findById(id);
//      .map(employee -> {
//        employee.setName(newEmployee.getName());
//        employee.setRole(newEmployee.getRole());
//        return repository.save(employee);
//      })
//      .orElseGet(() -> {
//        newEmployee.setId(id);
//        return repository.save(newEmployee);
//      });
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
