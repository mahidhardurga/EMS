package com.ems.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {
	@Id
	@GeneratedValue
	@Column(name = "empId")
	private int employee_id;
	@Column(name = "empName")
	private String employeeName;
	@Column(name = "email")
	private String emailId;
	@Column(name = "password", length = 60)
	private String password;
	@Column(name = "role")
	private String role;
	@Column(name = "isUserActive")
	private Boolean isUserActive = false;
	@Column(name = "employerName")
	private String employer_name;

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getemployeeName() {
		return employeeName;
	}

	public void setemployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public boolean isUserActive() {
		return isUserActive;
	}

	public void setUserActive(boolean isUserActive) {
		this.isUserActive = isUserActive;
	}

	public String getEmployer_name() {
		return employer_name;
	}

	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}

	@Override
	public String toString() {
		return "Employee [employee_id=" + employee_id + ", employeeName=" + employeeName + ", emailId=" + emailId
				+ ", password=" + password + ", role=" + role + ", isUserActive=" + isUserActive + ", employer_name="
				+ employer_name + "]";
	}

}
