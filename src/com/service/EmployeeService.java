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

	//Function for inserting an employee record
	public int insertEmployee(Employee employee) 
	{
		int personId = dbcon.createEmployee(employee);
		if( personId != -1)
			return personId;
		else
			return -1;
	}

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
