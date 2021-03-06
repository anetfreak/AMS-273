package com.service;

import javax.jws.WebService;

import com.dao.PDBConnection;
import com.domain.Customer;
@WebService
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
		int personId = (dbcon.createCustomer(customer));
		if(personId != 1)
			return personId;
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
	
	//Function for deleting a customer on basis of cutomer Id
			public boolean deleteCustomer(Integer customerId) {
				return dbcon.deleteCustomer(customerId);
			}
}
