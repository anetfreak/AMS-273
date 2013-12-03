package com.service;

import com.dao.PDBConnection;
import com.domain.Employee;

public class EmployeeService {

	PDBConnection dbcon = null;
	public EmployeeService()
	{
		dbcon = new PDBConnection();
	}
	public Employee getEmployee(Integer employeeId) {
		return dbcon.retriveEmployee(employeeId);
	}
	
	public boolean insertEmployee(Employee employee) {
		//TODO server side validations
		if(dbcon.createEmployee(employee))
		{
			System.out.println("Create Employee Success");
			return true;
		}
		return false;
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
