package com.ems.model;

import org.springframework.stereotype.Component;
@Component
public class EmployeeModel {

	private String employee_name;
	private String emailId;
	private String password;
	private String role;
	private String employer_name;

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmployer_name() {
		return employer_name;
	}

	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}

	@Override
	public String toString() {
		return "EmployeeModel [employee_name=" + employee_name + ", emailId=" + emailId + ", password=" + password
				+ ", role=" + role + ", employer_name=" + employer_name + "]";
	}

}
