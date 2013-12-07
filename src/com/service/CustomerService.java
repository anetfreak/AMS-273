package com.service;

import com.dao.PDBConnection;
import com.domain.Customer;

public class CustomerService {

	PDBConnection dbcon = null;
	public CustomerService()
	{
		dbcon = new PDBConnection();
	}
	
	public Customer[] getCustomers()
	{
		return dbcon.retriveCustomers();
	}
	
	public Customer getCustomer(Integer customerId) {
		return dbcon.retriveCustomer(customerId);
	}
	
	public Customer retriveCustomerbypId(Integer personId) {
		return dbcon.retriveCustomerbypId(personId);
	}
	
	public int insertCustomer(Customer customer) {
		//TODO server side validations
		int customerId = (dbcon.createCustomer(customer));
		if(customerId != 1)
			return customerId;
		else
			return -1;
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
