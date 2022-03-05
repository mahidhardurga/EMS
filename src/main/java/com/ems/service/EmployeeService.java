package com.ems.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ems.entity.Employee;
import com.ems.model.EmployeeModel;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeService implements UserDetailsService{
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	//@Autowired
	//private Verificationtokenrepository verificationtokenrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findbyName(username);
		if(employee == null) {
			logger.error("Employee Not Found");
			throw new UsernameNotFoundException("Employee Not Found");
		}else {
			logger.info("Employee found : {} ",username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(employee.getRole()));
		return new User(username, employee.getPassword(), authorities);
	}

	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Optional<Employee> getEmployee(Integer empId) {
		return employeeRepository.findById(empId);
	}

	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee updateEmployee(Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return employeeRepository.save(employee);
	}

	public void deleteEmployee(Integer empId) {
		employeeRepository.deleteById(empId);
	}
	
	public Employee getEmployeeByName(String name) {
		return employeeRepository.findbyName(name);
	}

	public Employee registerEmployee(EmployeeModel employeeModel) {
		Employee employee = new Employee();
		employee.setemployeeName(employeeModel.getEmployee_name());
		employee.setEmailId(employeeModel.getEmailId());
		employee.setPassword(passwordEncoder.encode(employeeModel.getPassword()));
		employee.setRole(employeeModel.getRole());
		employee.setEmployer_name(employeeModel.getEmployer_name());
		employeeRepository.save(employee);
		return employee;
	}

	/*
	 * public void saveVerificationTokenForUser(String token, Employee employee) {
	 * VerificationToken verificationToken = new VerificationToken(employee, token);
	 * verificationtokenrepository.save(verificationToken); }
	 */

	/*
	 * public String validateVerificationToken(String token) { VerificationToken
	 * verificationToken = verificationtokenrepository.findByToken(token); if (null
	 * == verificationToken) { return "INVALID"; }
	 * 
	 * Employee employee = verificationToken.getEmployee(); Calendar calendar =
	 * Calendar.getInstance(); if ((verificationToken.getExpirationTime().getTime()
	 * - calendar.getTime().getTime()) <= 0) {
	 * verificationtokenrepository.delete(verificationToken); return "EXPIRED"; }
	 * employee.setUserActive(true); employeeRepository.save(employee); return
	 * "VALID"; }
	 */

	/*
	 * public VerificationToken generateNewVerificationToken(String oldToken) {
	 * VerificationToken verificationToken =
	 * verificationtokenrepository.findByToken(oldToken);
	 * verificationToken.setToken(UUID.randomUUID().toString());
	 * verificationtokenrepository.save(verificationToken); return
	 * verificationToken; }
	 */

}
