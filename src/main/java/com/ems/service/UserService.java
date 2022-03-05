package com.ems.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ems.entity.Employee;


public class UserService implements UserDetailsService{
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeService.getEmployeeByName(username);
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

}
