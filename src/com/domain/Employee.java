package com.domain;

public class Employee {
	
	private Integer employeeId;
	private String workDesc;
	private Integer position;
	private String hireDate;
	private Person person;
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
}
