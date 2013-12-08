package com.service;

import javax.jws.WebService;

import com.dao.PDBConnection;
import com.domain.Customer;
import com.domain.Employee;
@WebService
public class EmployeeService {

	PDBConnection dbcon = null;
	public EmployeeService()
	{
		dbcon = new PDBConnection();
	}

	public Employee[] getEmployees()
	{
		return dbcon.retriveEmployees();
	}
	
	//Function used for checking the login of the Employee
	public Employee getEmployee(Integer employeeId) {
		return dbcon.retriveEmployee(employeeId);
	}

	//Function used for checking the login of the Employee
	public Employee retriveEmployeebypId(Integer personId) {
		return dbcon.retriveEmployeebypId(personId);
	}

	//Function used for searching an employee based on FirstName and LastName
	public Employee[] retriveEmployeesbyName(String firstName, String lastName) {
		return dbcon.retriveEmployeesbyName(firstName, lastName);
	}
	
	//Function for inserting an employee record
	public int insertEmployee(Employee employee) 
	{
		int personId = dbcon.createEmployee(employee);
		if( personId != -1)
			return personId;
		else
			return -1;
	}

	//Function for updating the record of an employee
	public boolean updateEmployee(Employee employee) {
		//TODO server side validations
		if(dbcon.updateEmployee(employee))
		{
			System.out.println("Update Employee Success");
			return true;
		}
		return false;
	}
}
