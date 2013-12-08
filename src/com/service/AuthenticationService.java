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
		int personId = (dbcon.signIn(username, password, 2));
		if( personId != -1)
			return personId;
		else
			return -1;
	}

	public int customerSignUp(Customer customer) {
		int custId = custromerService.insertCustomer(customer);
		if (custId != -1)
			return custId;
		else
		return -1;
	}

	public int signInEmployee(String username, String password) {
		//TODO need to check person type too
		int personId = (dbcon.signIn(username, password, 1));
		if( personId != -1)
			return (personId);
		else
			return -1;
	}

	public int employeeSignUp(Employee employee) {
		int personId = employeeService.insertEmployee(employee);
		if(personId != -1)
			return personId;
		else
			return -1;	
	}

	public boolean updateCustInformation(Customer customer) {
		return custromerService.updateCustomer(customer);
	}

	public boolean updateEmpInformation(Employee employee) {
		return employeeService.updateEmployee(employee);
	}
}
