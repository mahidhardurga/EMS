package com.ems.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class VerificationToken {

	private static final int EXPIRATION_TIME = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;
	private Date expirationTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empId", nullable = false, foreignKey = @ForeignKey(name = "FK_EMPLOYEE_VERIFY_TOKEN"))
	private Employee employee;

	public VerificationToken() {
	}

	public VerificationToken(Employee employee, String token) {
		super();
		this.employee = employee;
		this.token = token;
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	}

	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	}

	private Date calculateExpirationDate(int expirationTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


}
