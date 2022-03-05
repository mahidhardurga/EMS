package com.ems.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ems.entity.Employee;
import com.ems.model.EmployeeModel;
import com.ems.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/v1/hello")
	public String hello() {
		return "Hello Mahidhar!";
	}
	
	@PostMapping("/registerEmployee")
	public String registerEmployee(@RequestBody EmployeeModel employeeModel, final HttpServletRequest request) {
		Employee employee = employeeService.registerEmployee(employeeModel);
		//publisher.publishEvent(new RegistrationCompleteEvent(employee, applicationUrl(request)));
		return "Success";
	}

	@GetMapping("/v1/getEmployee/{empId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Integer empId) {
		Optional<Employee> emp = null != empId ? employeeService.getEmployee(empId) : null;
		return emp.isPresent() ? new ResponseEntity<Employee>(emp.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/v1/getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> allEmployees = (List<Employee>) employeeService.getAllEmployees();
		return !allEmployees.isEmpty() ? new ResponseEntity<List<Employee>>(allEmployees, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/v1/")
	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> allEmployees = (List<Employee>) employeeService.getAllEmployees();
		return !allEmployees.isEmpty() ? new ResponseEntity<List<Employee>>(allEmployees, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/*
	 * @PostMapping("/v1/addEmployee") public ResponseEntity<Employee>
	 * addEmployee(@RequestBody Employee employee) { Employee emp = employee != null
	 * ? employeeService.addEmployee(employee) : null; return null != emp ? new
	 * ResponseEntity<Employee>(emp, HttpStatus.OK) : new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 */

	@PutMapping("/v1/updateEmployee")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Employee emp = employee != null ? employeeService.updateEmployee(employee) : null;
		return null != emp ? new ResponseEntity<Employee>(emp, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/*
	 * @PutMapping("/updateEmployee/{empId}") public ResponseEntity<Employee>
	 * updateEmployeeById(@PathVariable Integer empId, @RequestBody Employee
	 * employee) { Employee emp = employee != null ?
	 * employeeService.updateEmployee(empId,employee) : null; return null != emp ?
	 * new ResponseEntity<Employee>(emp, HttpStatus.OK) : new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 */
	
	@DeleteMapping("/v1/deleteEmployee/{empId}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable Integer empId) {
		if (empId != null) {
			employeeService.deleteEmployee(empId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
