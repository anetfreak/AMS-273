package com.service;

import javax.jws.WebService;

import com.dao.PDBConnection;
import com.domain.Customer;
import com.domain.Employee;
import com.domain.PersonType;

@WebService
public class AuthenticationService {
	
	CustomerService custromerService = null;
	EmployeeService employeeService = null;
	PDBConnection dbcon = null;
	public AuthenticationService()
	{
		custromerService = new CustomerService();
		employeeService = new EmployeeService();
		dbcon = new PDBConnection();
	}
	
	public int signInCustomer(String username, String password) {
		//TODO need to check person type too
		if((dbcon.signIn(username, password, 2)) != -1)
			return (dbcon.signIn(username, password, 2));
		return -1;
	}
	
	public boolean customerSignUp(Customer customer) {
		if(custromerService.insertCustomer(customer))
			return true;
		return false;
	}
	
	public int signInEmployee(String username, String password) {
		//TODO need to check person type too
		if((dbcon.signIn(username, password, 1)) != -1)
			return (dbcon.signIn(username, password, 1));
		return -1;
	}
	
	public boolean employeeSignUp(Employee employee) {
		if(employeeService.insertEmployee(employee))
			return true;
		return false;
	}
	
	public boolean updateCustInformation(Customer customer) {
		return false;
	}
	
	public boolean updateEmpInformation(Employee employee) {
		return false;
	}
}
