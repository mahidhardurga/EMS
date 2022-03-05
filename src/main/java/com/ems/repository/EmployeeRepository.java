package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	@Query("SELECT e from Employee e where e.employeeName = ?1")
	Employee findbyName(String name);
	
}
