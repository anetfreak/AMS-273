package com.service;

import com.dao.PDBConnection;
import com.domain.Customer;

public class CustomerService {

	PDBConnection dbcon = null;
	public CustomerService()
	{
		dbcon = new PDBConnection();
	}
	
	public Customer getCustomer(Integer customerId) {
		return dbcon.retriveCustomer(customerId);
	}
	
	public Customer retriveCustomerbypId(Integer personId) {
		return dbcon.retriveCustomerbypId(personId);
	}
	
	public boolean insertCustomer(Customer customer) {
		//TODO server side validations
		if(dbcon.createCustomer(customer))
		{
			System.out.println("Create Customer Success");
			return true;
		}
		return false;
	}
	
	public boolean updateCustomer(Customer customer) {
		//TODO server side validations
		if(dbcon.updateCustomer(customer))
		{
			System.out.println("Update Customer Success");
			return true;
		}
		return false;
	}
}
