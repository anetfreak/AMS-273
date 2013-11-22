package com.service;

import javax.jws.WebService;

import com.domain.Customer;
import com.domain.Employee;

@WebService
public class AuthenticationService {
	public boolean signIn(String username, String password) {
		return false;
	}
	
	public boolean customerSignUp(Customer customer) {
		return false;
	}
	
	public boolean employeeSignUp(Employee employee) {
		return false;
	}
	
	public boolean updateCustInformation(Customer customer) {
		return false;
	}
	
	public boolean updateEmpInformation(Employee employee) {
		return false;
	}
}
