package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.domain.Customer;
import com.domain.Employee;
import com.domain.Flight;
import com.domain.FlightTime;
import com.domain.Journey;
import com.domain.Location;
import com.domain.Person;
import com.domain.PersonType;
import com.domain.Reservation;
import com.domain.Traveller;


public class PDBConnection {
	SQLConnectionPool pool = SQLConnectionPool.getPool();
	Connection con = null;
	Statement s = null;
	ResultSet rs = null;

	public PDBConnection()
	{
		/*
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/ams_schema",
					"root", "mysql");

			if (!con.isClosed()) {
				System.out
				.println("Successfully Connected to Mysql server using TCP/IP");

			}

			s = con.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 */
	}

	public int signIn(String username, String password, Integer type) 
	{
		if(username == null || username.isEmpty() || username == "" || username == null || username.isEmpty() || username == "")
			return -1;

		String query = "select * from person where username = ? AND password = ? AND persontype = ?";
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return -1;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				pool.closeConn(con);
				return (rs.getInt("personId"));
			}


		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}
		pool.closeConn(con);
		return -1;
	}

	public Integer createPerson(Person person,Connection con)
	{
		if(person == null)
			return -1;
		int rc = 0;
		int personId = 0;
		/*									1			2		3		4		5	  6	   7	8		  9		10		  		*/
		String query = "insert into person(firstName,lastName,address,city,state,zip,personType,DOB,username,password) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try 
		{	
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return -1;
			}*/
			PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

			//ps.setInt(1,3456789);			
			ps.setString(1, person.getFirstName());
			ps.setString(2,person.getLastName());
			ps.setString(3,person.getAddress());
			ps.setString(4,person.getCity());
			ps.setString(5,person.getState());
			ps.setInt(6,person.getZip());
			ps.setInt(7,person.getPersonType());
			ps.setString(8,person.getDOB());
			ps.setString(9,person.getUsername());
			ps.setString(10,person.getPassword());

			rc = ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if(rs.next())
				personId = rs.getInt(1);
			System.out.println("Person id = " + personId);
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create Person Successful");
			//pool.closeConn(con);
			return personId;
		}
		//pool.closeConn(con);
		return -1;	
	}

	public Person retrivePerson(Integer personId)
	{
		if(personId == -1)
			return null;

		Person person = null;

		String query = "select * from person where personId = ?";
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if (rs.next()) {
				person = new Person();
				person.setPersonId(rs.getInt("personId"));
				person.setFirstName(rs.getString("firstName"));
				person.setLastName(rs.getString("lastName"));
				person.setAddress(rs.getString("address"));
				person.setCity(rs.getString("city"));
				person.setState(rs.getString("state"));
				person.setZip(rs.getInt("zip"));
				person.setPersonType(rs.getInt("personType"));
				person.setDOB(rs.getString("dob"));
				person.setUsername(rs.getString("username"));
				person.setPassword(rs.getString("password"));
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(person != null)
		{
			System.out.println("Retrive Person Successful");
			pool.closeConn(con);
			return person;
		}
		pool.closeConn(con);
		return null;
	}

	public boolean updatePerson(Person person,Connection con)
	{
		if(person == null)
			return false;

		int rc = 0;

		Integer personID = person.getPersonId();
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		String address = person.getAddress();
		String city = person.getCity();
		String state = person.getState();
		Integer zip = person.getZip();
		Integer personType = person.getPersonType();
		String dob = person.getDOB();
		String username = person.getUsername();
		String password = person.getPassword();

		String query = "update person set " +
		"firstName = ? ," +		//1
		"lastName = ? ," +		//2
		"address = ? ," +			//3
		"city = ? ," +			//4
		"state = ? ," +			//5
		"zip = ? ," +				//6
		"personType = ? ," +		//7
		"dob = ? ," +				//8
		"username = ? ," +		//9
		"password = ? " +		//10
		"where personID = ?";	//11
		try 
		{
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, address);
			ps.setString(4, city);
			ps.setString(5, state);
			ps.setInt(6, zip);
			ps.setInt(7, personType);
			ps.setString(8, dob);
			ps.setString(9, username);
			ps.setString(10, password);
			ps.setInt(11, personID);

			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Update Person Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}

	public boolean deletePerson(Integer personId,Connection con)
	{
		if(personId == -1)
			return false;

		int rc = 0;

		String query = "delete from person where personId = ?";

		try 
		{	
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(11, personId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete Person Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;	
	}


	public int createCustomer(Customer customer)
	{
		if(customer == null)
			return -1;

		int rc = 0;
		int customerId = -1;
		int personId = -1;
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return -1;
			}
			//setting autocommit false for transaction support
			con.setAutoCommit(false);
			personId = createPerson(customer.getPerson(),con);
			if(personId > 0)
			{
				/*										1			2		3				4	*/
				String query = "insert into customer(customerId,personId,passportNumber,nationality) " +
				"values (?, ?, ?, ?)";

				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, customer.getCustomerId());
				ps.setInt(2, personId);
				ps.setString(3, customer.getPassportNumber());
				ps.setString(4, customer.getNationality());

				rc = ps.executeUpdate();
				if(rc > 0)
				{
					rs = ps.getGeneratedKeys();
					if(rs.next())
						customerId = rs.getInt(1);
					System.out.println("Employee id = " + customerId);
					con.commit();
				}
				else
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			} 
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		}
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		//createReservation(customer.getCustomerId(),customer.getReservation());

		if (rc > 0) {
			System.out.println("Create Customer Successful");
			pool.closeConn(con);
			return personId;
		}
		pool.closeConn(con);
		return -1;
	}

	public Customer[] retriveCustomers()
	{

		List<Customer> cust_list = new ArrayList<Customer>();
		Customer[] customers = null;
		Customer customer = null;

		String query = "select * from customer";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = new Customer();
				customer.setCustomerId(rs.getInt("customerId"));
				customer.setPassportNumber(rs.getString("passportNumber"));
				customer.setNationality(rs.getString("nationality"));
				Person person = retrivePerson(rs.getInt("personId")); 
				customer.setPerson(person);
				cust_list.add(customer);
				//Reservation reservation = retriveReservationByCustId(rs.getInt("customerId"));
				//customer.setReservation(reservation);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}
		if(!cust_list.isEmpty())
		{
			System.out.println("Retrive customers Successful");
			customers = new Customer[cust_list.size()];
			customers = cust_list.toArray(customers);
			pool.closeConn(con);
			return customers;
		}

		pool.closeConn(con);
		return null;
	}
	
	public Customer retriveCustomer(Integer customerId)
	{
		if(customerId < 0)
			return null;

		Customer customer = null;

		String query = "select * from customer where customerId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId);
			rs = ps.executeQuery();
			if (rs.next()) {
				customer = new Customer();
				customer.setCustomerId(rs.getInt("customerId"));
				customer.setPassportNumber(rs.getString("passportNumber"));
				customer.setNationality(rs.getString("nationality"));
				Person person = retrivePerson(rs.getInt("personId")); 
				customer.setPerson(person);
				//Reservation reservation = retriveReservationByCustId(rs.getInt("customerId"));
				//customer.setReservation(reservation);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(customer != null)
		{
			System.out.println("Retrive customer Successful");
			pool.closeConn(con);
			return customer;
		}
		pool.closeConn(con);
		return null;
	}

	public Customer retriveCustomerbypId(Integer personId)
	{
		if(personId < 0)
			return null;

		Customer customer = null;

		String query = "select * from customer where personId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if (rs.next()) {
				customer = new Customer();
				customer.setCustomerId(rs.getInt("customerId"));
				customer.setPassportNumber(rs.getString("passportNumber"));
				customer.setNationality(rs.getString("nationality"));
				Person person = retrivePerson(rs.getInt("personId")); 
				customer.setPerson(person);
				//Reservation reservation = retriveReservationByCustId(rs.getInt("customerId"));
				//customer.setReservation(reservation);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(customer != null)
		{
			System.out.println("Retrive customer Successful");
			pool.closeConn(con);
			return customer;
		}
		pool.closeConn(con);
		return null;
	}
	
	public boolean updateCustomer(Customer customer)
	{
		if(customer == null)
			return false;

		int rc = 0;

		Integer customerId = customer.getCustomerId();
		String passportNumber = customer.getPassportNumber();
		String nationality = customer.getNationality();
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			//setting autocommit false for transaction support
			con.setAutoCommit(false);
			if(updatePerson(customer.getPerson(),con))
			{
				String query = "update customer set " +
				"passportNumber = ? ," +		//1
				"nationality = ? " +			//2
				"where customerId = ?";			//3


				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, passportNumber);
				ps.setString(2, nationality);
				ps.setInt(3, customerId);

				rc = ps.executeUpdate(query);
				if(rc > 0)
				{
					con.commit();
				}
				else
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			}
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		}
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		//updateReservation(customer.getReservation());


		if (rc > 0) {
			System.out.println("Update Customer Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean deleteCustomer(Integer customerId)
	{
		if(customerId < 0)
			return false;

		int rc = 0;
		int personId = -1;
		String query = "delete from customer where customerId = ?";
		String query1 = "select personId from customer where customerId = ?";
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			con.setAutoCommit(false);
			PreparedStatement ps1 = con.prepareStatement(query1);
			ps1.setInt(1, customerId);
			rs = ps1.executeQuery();
			if(rs.next())
				personId = rs.getInt("personId");


			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId);
			rc = ps.executeUpdate();
			if(rc > 0 && personId > -1)
			{
				if(deletePerson(personId,con))
				{
					con.commit();
				}
				else
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			}
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}

		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete Customer Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;	
	}

	public int createEmployee(Employee employee) {

		if(employee == null)
			return -1;

		int rc = 0;
		int employeeId = -1;
		int personId = -1;
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return -1;
			}
			con.setAutoCommit(false);
			personId = createPerson(employee.getPerson(),con);
			if(personId > 0)
			{
				/*										1			2		3		4		5	*/
				String query = "insert into employee(employeeId,personId,workDesc,position,hireDate) " +
				"values (?, ?, ?, ?, ?)";


				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, employee.getEmployeeId());
				ps.setInt(2, personId);
				ps.setString(3, employee.getWorkDesc());
				ps.setString(4, employee.getPosition());
				ps.setString(5,employee.getHireDate());
				rc = ps.executeUpdate();
				if(rc > 0)
				{
					con.commit();
					rs = ps.getGeneratedKeys();
					if(rs.next())
						employeeId = rs.getInt(1);
					System.out.println("Employee id = " + employeeId);
				}
				else
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			} 
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		}
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}


		if (rc > 0) {
			System.out.println("Create Employee Successful");
			pool.closeConn(con);
			return personId;
		}
		pool.closeConn(con);
		return -1;
	}
	
	public Employee[] retriveEmployees()
	{
		
		List<Employee> emp_list = new ArrayList<Employee>();
		Employee[] employees = null;
		Employee employee = null;

		String query = "select * from employee";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employeeId"));
				employee.setWorkDesc(rs.getString("workDesc"));
				employee.setPosition(rs.getString("position"));
				employee.setHireDate(rs.getString("hireDate"));

				Person person = retrivePerson(rs.getInt("personId")); 
				employee.setPerson(person);
				emp_list.add(employee);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!emp_list.isEmpty())
		{
			System.out.println("Retrive customers Successful");
			employees = new Employee[emp_list.size()];
			employees = emp_list.toArray(employees);
			pool.closeConn(con);
			return employees;
		}
		pool.closeConn(con);
		return null;
	}

	public Employee retriveEmployee(Integer employeeId)
	{
		if(employeeId < 0)
			return null;

		Employee employee = null;

		String query = "select * from employee where employeeId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employeeId"));
				employee.setWorkDesc(rs.getString("workDesc"));
				employee.setPosition(rs.getString("position"));
				employee.setHireDate(rs.getString("hireDate"));

				Person person = retrivePerson(rs.getInt("personId")); 
				employee.setPerson(person);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(employee != null)
		{
			pool.closeConn(con);
			System.out.println("Retrive employee Successful");
			return employee;
		}
		pool.closeConn(con);
		return null;
	}

	public Employee retriveEmployeebypId(Integer personId)
	{
		if(personId < 0)
			return null;

		Employee employee = null;

		String query = "select * from employee where personId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if (rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employeeId"));
				employee.setWorkDesc(rs.getString("workDesc"));
				employee.setPosition(rs.getString("position"));
				employee.setHireDate(rs.getString("hireDate"));

				Person person = retrivePerson(rs.getInt("personId")); 
				employee.setPerson(person);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(employee != null)
		{
			pool.closeConn(con);
			System.out.println("Retrive employee Successful");
			return employee;
		}
		pool.closeConn(con);
		return null;
	}
	
	public boolean updateEmployee(Employee employee)
	{
		if(employee == null)
			return false;

		int rc = 0;

		Integer employeeId = employee.getEmployeeId();
		String workDesc = employee.getWorkDesc();
		String position = employee.getPosition();
		String hireDate = employee.getHireDate();
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			con.setAutoCommit(false);
			if(updatePerson(employee.getPerson(),con))
			{
				String query = "update employee set " +
				"workDesc = ? ," +			//1
				"position = ? ," +			//2
				"hireDate = ? " +			//3
				"where employeeId = ?";		//4


				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, workDesc);
				ps.setString(2, position);
				ps.setString(3, hireDate);
				ps.setInt(4, employeeId);
				rc = ps.executeUpdate();
				if(rc > 0)
				{
					con.commit();
				}
				else
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			}
		}
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}


		if (rc > 0) {
			pool.closeConn(con);
			System.out.println("Update Employee Successful");
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean deleteEmployee(Integer employeeId)
	{
		if(employeeId < 0)
			return false;

		int rc = 0;
		int personId = -1;
		String query = "delete from customer where employeeId = ?";
		String query1 = "select personId from employee where employeeId = ?";
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			con.setAutoCommit(false);
			PreparedStatement ps1 = con.prepareStatement(query1);
			ps1.setInt(1, employeeId);
			rs = ps1.executeQuery();
			if(rs.next())
				personId = rs.getInt("personId");

			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rc = ps.executeUpdate();
			if(rc > 0 && personId > -1)
			{
				if(deletePerson(personId,con))
				{
					con.commit();
				}
				else
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			}
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}

		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			pool.closeConn(con);
			System.out.println("Delete employee Successful");
			return true;
		}
		pool.closeConn(con);
		return false;	
	}





	public boolean createReservation(Reservation reservation)
	{
		if(reservation == null)
			return false;

		int rc = 0;
		int reservationId = -1;
		/*											1			2				3			4					*/
		String query = "insert into reservation(reservationNo,customerId,reservationStatus,seatsBooked) " +
		"values (?,?,?,?)";


		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(query);
			//ps.setInt(1, null); //Auto Increment
			ps.setString(1, reservation.getReservationNo());
			ps.setInt(2, reservation.getCustomerId());
			ps.setInt(3, reservation.getReservationStatus());
			ps.setInt(4, reservation.getSeatsBooked());

			rc = ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if(rs.next())
				reservationId = rs.getInt(1);
			System.out.println("reservationId = " + reservationId);

			if(rc > 0 && reservationId > 0)
			{
				Traveller[] travellers =  reservation.getTravellers();
				for(int i = 0; i< travellers.length;i++)
				{
					if(!createTravller(reservationId,travellers[i],con))
					{
						try {
							System.err.print("Transaction is being rolled back");
							con.rollback();
							pool.closeConn(con);
							return false;
						} catch(SQLException excep) {
							excep.printStackTrace();
						}
					}
				}
				Journey[] journey = reservation.getJourney();
				for(int j = 0; j<journey.length;j++)
				{
					if(!createJourney(reservationId,journey[j],con))

					{
						try {
							System.err.print("Transaction is being rolled back");
							con.rollback();
							pool.closeConn(con);
							return false;
						} catch(SQLException excep) {
							excep.printStackTrace();
						}
					}
				}
				con.commit();
			}
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
				pool.closeConn(con);
				return false;
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}



		if (rc > 0) {
			System.out.println("Create reservation Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}


		public Reservation[] retriveReservations()
	{
		List<Reservation> res_list = new ArrayList<Reservation>();
		Reservation[] reservations = null;
		Reservation reservation = null;

		String query = "select * from reservation";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				reservation = new Reservation();
				reservation.setReservationId(rs.getInt("reservationId"));
				reservation.setReservationNo(rs.getString("reservationNo"));
				reservation.setCustomerId(rs.getInt("customerId"));
				reservation.setReservationStatus(rs.getInt("reservationStatus"));
				//reservation.setFlightId(rs.getInt("flightId"));
				reservation.setSeatsBooked(rs.getInt("seatsBooked"));

				Traveller[] travellers = retriveTravellers(rs.getInt("reservationId"));
				reservation.setTravellers(travellers);

				Journey[] journey = retriveJourney(rs.getInt("reservationId"));
				reservation.setJourney(journey);
				res_list.add(reservation);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!res_list.isEmpty())
		{
			System.out.println("Retrive reservations Successful");
			reservations = new Reservation[res_list.size()];
			reservations = res_list.toArray(reservations);
			pool.closeConn(con);
			return reservations;
		}
		pool.closeConn(con);
		return null;
	}

	//retrive by reservationId
	public Reservation retriveReservationbyResId(Integer reservationId)
	{
		if(reservationId < 0)
			return null;

		Reservation reservation = null;

		String query = "select * from reservation where reservationId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId); 

			rs = ps.executeQuery();
			if (rs.next()) {
				reservation = new Reservation();
				reservation.setReservationId(rs.getInt("reservationId"));
				reservation.setReservationNo(rs.getString("reservationNo"));
				reservation.setCustomerId(rs.getInt("customerId"));
				reservation.setReservationStatus(rs.getInt("reservationStatus"));
				//reservation.setFlightId(rs.getInt("flightId"));
				reservation.setSeatsBooked(rs.getInt("seatsBooked"));

				Traveller[] travellers = retriveTravellers(reservationId);
				reservation.setTravellers(travellers);

				Journey[] journey = retriveJourney(reservationId);
				reservation.setJourney(journey);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(reservation != null)
		{
			System.out.println("Retrive reservation Successful");
			pool.closeConn(con);
			return reservation;
		}
		pool.closeConn(con);
		return null;
	}

	//retrive by customer Id
	public Reservation retriveReservationByCustId(Integer customerId)
	{
		if(customerId < 0)
			return null;

		Reservation reservation = null;

		String query = "select * from reservation where customerId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId); 

			rs = ps.executeQuery();
			if (rs.next()) {
				reservation = new Reservation();
				reservation.setReservationId(rs.getInt("reservationId"));
				reservation.setReservationNo(rs.getString("reservationNo"));
				reservation.setCustomerId(rs.getInt("customerId"));
				reservation.setReservationStatus(rs.getInt("reservationStatus"));
				//reservation.setFlightId(rs.getInt("flightId"));
				reservation.setSeatsBooked(rs.getInt("seatsBooked"));

				Traveller[] travellers = retriveTravellers(rs.getInt("reservationId"));
				reservation.setTravellers(travellers);

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(reservation != null)
		{
			System.out.println("Retrive reservation Successful");
			pool.closeConn(con);
			return reservation;
		}
		pool.closeConn(con);
		return null;
	}
	//retrive by reservationNo
	public Reservation retriveReservationbyResNo(String reservationNo)
	{
		if(reservationNo == null || reservationNo.isEmpty() || reservationNo == "")
			return null;

		Reservation reservation = null;

		String query = "select * from reservation where reservationNo = ?" ;

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, reservationNo); 
			rs = ps.executeQuery();
			if (rs.next()) {
				reservation = new Reservation();
				reservation.setReservationId(rs.getInt("reservationId"));
				reservation.setReservationNo(rs.getString("reservationNo"));
				reservation.setCustomerId(rs.getInt("customerId"));
				reservation.setReservationStatus(rs.getInt("reservationStatus"));
				//reservation.setFlightId(rs.getInt("flightId"));
				reservation.setSeatsBooked(rs.getInt("seatsBooked"));

				Traveller[] travellers = retriveTravellers(rs.getInt("reservationId"));
				reservation.setTravellers(travellers);

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(reservation != null)
		{
			pool.closeConn(con);
			System.out.println("Retrive reservation Successful");
			return reservation;
		}
		pool.closeConn(con);
		return null;
	}

	public boolean updateReservation(Reservation reservation)
	{
		if(reservation == null)
			return false;

		int rc = 0;

		Integer reservationId = reservation.getReservationId();
		String reservationNo = reservation.getReservationNo();
		Integer customerId = reservation.getCustomerId();
		Integer reservationStatus = reservation.getReservationStatus();
		Integer seatsBooked = reservation.getSeatsBooked();
		Traveller[] travellers = reservation.getTravellers();
		Journey[] journey = reservation.getJourney();

		String query = "update reservation set " +
		"reservationNo = ? ," +
		"customerId = ? ," +
		"reservationStatus ? ," +
		"seatsBooked = ? " +
		"where reservationId ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, reservationNo);
			ps.setInt(2, customerId);
			ps.setInt(3, reservationStatus);
			ps.setInt(4, seatsBooked);
			ps.setInt(5, reservationId);

			rc = ps.executeUpdate();
			for(int i = 0; i< travellers.length;i++)
				if(!updateTravller(travellers[i],con))
				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			if(!updateJourney(reservationId,journey,con))
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
			con.commit();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}	
			pool.closeConn(con);
			sqle.printStackTrace();
		}





		if (rc > 0) {
			System.out.println("Update reservation Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean deleteReservation(Integer reservationId)
	{
		if(reservationId < 0)
			return false;

		int rc = 0;
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			con.setAutoCommit(false);
			if(!deleteTravellers(reservationId,con))
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
			if(!deleteJourney(reservationId,con))
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
			String query = "delete from reservation where reservationId = ?";


			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rc = ps.executeUpdate();
			if(rc > 0)
			{
				con.commit();
			}
			else
			{
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete reservation Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean deleteReservationByCustomer(Integer customerId)
	{
		if(customerId < 0)
			return false;

		int rc = 0;

		String query = "delete from reservation where customerId = ?";

		//TODO deleteTravellers(reservationId); //1. get reservation id 2. delete travellers and journey 3. delete reservation
		//TODO deleteJourney();
		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete reservation Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}


	public boolean createTravller(Integer reservationId,Traveller traveller,Connection con)
	{
		if(reservationId < 0 || traveller == null)
			return false;

		int rc = 0;
		/*											1		2	 3	  4	  	5	*/
		String query = "insert into traveller(firstName,lastName,age,sex,reservationId) " +
		"values (?,?,?,?,?)";


		try 
		{
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			//ps.setInt(1, travellerId); //auto Increment
			ps.setString(1, traveller.getFirstName());
			ps.setString(2, traveller.getLastName());
			ps.setInt(3, traveller.getAge());
			ps.setString(4, traveller.getSex());
			ps.setInt(5, reservationId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create Traveller Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}

	public Traveller[] retriveTravellers(Integer reservationId)
	{
		if(reservationId < 0)
			return null;

		List<Traveller> travellerList = new ArrayList<Traveller>();
		Traveller travelleritem = null;
		Traveller[] travellers = null;
		String query = "select * from reservation where reservationId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId); 
			rs = ps.executeQuery();

			while (rs.next()) {
				travelleritem = new Traveller();

				travelleritem.setTravellerId(rs.getInt("travellerId"));
				travelleritem.setFirstName(rs.getString("firstName"));
				travelleritem.setLastName(rs.getString("lastName"));
				travelleritem.setAge(rs.getInt("age"));
				travelleritem.setSex(rs.getString("sex"));

				travellerList.add(travelleritem);
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!travellerList.isEmpty())
		{
			travellers = new Traveller[travellerList.size()];
			travellers = travellerList.toArray(travellers);

			System.out.println("Retrive traveller Successful");
			pool.closeConn(con);
			return travellers;
		}
		pool.closeConn(con);
		return null;
	}


	public boolean updateTravller(Traveller traveller,Connection con)
	{
		if(traveller == null)
			return false;

		int rc = 0;

		Integer travellerId = traveller.getTravellerId();
		String firstName = traveller.getFirstName();
		String lastName = traveller.getLastName();
		Integer age = traveller.getAge();
		String sex = traveller.getSex();
		//TODO need to check if need update reservationId

		String query = "update traveller set " +
		"firstName = ? ," +
		"lastName = ? ," +
		"age = ? ," +
		"sex = ? " +
		"where travellerId = ?";

		try 
		{
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,firstName);
			ps.setString(2, lastName);
			ps.setInt(3, age);
			ps.setString(4, sex);
			ps.setInt(1, travellerId); 
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Update traveller Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}

	//Delete on the basis of reservationId
	public boolean deleteTravellers(Integer reservationId,Connection con)
	{
		if(reservationId < 0)
			return false;

		int rc = 0;

		String query = "delete from traveller where reservationId = ?";

		try 
		{
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete travellers Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}

	//Delete on the basis of travellerId
	public boolean deleteTraveller(Integer travellerId)
	{
		if(travellerId < 0)
			return false;

		int rc = 0;

		String query = "delete from traveller where travellerId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, travellerId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete traveller Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}


	public boolean createLocation(Location location)
	{
		if(location == null)
			return false;

		int rc = 0;
		/*										1		  2		  3			4	*/
		String query = "insert into location(locationId,state,stateCode,airportCode) " +
		"values (?,?,?,?)";


		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, location.getLocationId());
			ps.setString(2, location.getState());
			ps.setString(3, location.getStateCode());
			ps.setString(4, location.getAirportCode());
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create location Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public Location retriveLocation(Integer locationId)
	{
		if(locationId == null)
			return null;

		Location location = null;

		String query = "select * from location where locationId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, locationId);
			rs = ps.executeQuery();
			if (rs.next()) {
				location = new Location();
				location.setAirportCode(rs.getString("airportCode"));
				location.setLocationId(rs.getInt("locationId"));
				location.setState(rs.getString("state"));
				location.setStateCode(rs.getString("stateCode"));
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			pool.closeConn(con);
			return location;
		}
		return null;
	}

	public Location retriveLocationByStateCode(String stateCode)
	{
		if(stateCode == null || stateCode.isEmpty() || stateCode == "")
			return null;

		Location location = null;

		String query = "select * from location where stateCode = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, stateCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				location = new Location();
				location.setAirportCode(rs.getString("airportCode"));
				location.setLocationId(rs.getInt("locationId"));
				location.setState(rs.getString("state"));
				location.setStateCode(rs.getString("stateCode"));
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			pool.closeConn(con);
			return location;
		}
		pool.closeConn(con);
		return null;
	}

	public Location retriveLocationByState(String state)
	{
		if(state == null || state.isEmpty() || state == "")
			return null;

		Location location = null;

		String query = "select * from location where state = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, state);
			rs = ps.executeQuery();
			if (rs.next()) {
				location = new Location();
				location.setAirportCode(rs.getString("airportCode"));
				location.setLocationId(rs.getInt("locationId"));
				location.setState(rs.getString("state"));
				location.setStateCode(rs.getString("stateCode"));
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			pool.closeConn(con);
			return location;
		}
		pool.closeConn(con);
		return null;
	}

	public Location retriveLocationByAirportCode(String airportCode)
	{
		if(airportCode == null || airportCode.isEmpty() || airportCode == "")
			return null;

		Location location = null;

		String query = "select * from location where airportCode = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, airportCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				location = new Location();
				location.setAirportCode(rs.getString("airportCode"));
				location.setLocationId(rs.getInt("locationId"));
				location.setState(rs.getString("state"));
				location.setStateCode(rs.getString("stateCode"));
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			pool.closeConn(con);
			return location;
		}
		pool.closeConn(con);
		return null;
	}

	public boolean updateLocation(Location location)
	{
		if(location == null)
			return false;

		int rc = 0;

		Integer locationId = location.getLocationId();
		String state = location.getState();
		String stateCode = location.getStateCode();
		String airportCode = location.getAirportCode();


		String query = "update location set " +
		"state = ? ," +				//1
		"stateCode = ? ," +			//2
		"airportCode = ? " +		//3
		"where locationId = ?";		//4

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, state);
			ps.setString(2, stateCode);
			ps.setString(3, airportCode);
			ps.setInt(4, locationId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Update location Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean deleteLocation(Integer locationId)
	{
		if(locationId < 0)
			return false;

		int rc = 0;

		String query = "delete from location where locationId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, locationId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete location Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean createFlight(Flight flight)
	{
		if(flight == null)
			return false;

		int rc = 0;
		/*										1		2			3		4		5				*/
		String query = "insert into flight(flightNo,airlineName,source,destination,noOfSeats) " +
		"values (?,?,?,?,?,?)";


		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			//ps.setInt(1, flight.getFlightId());
			ps.setString(1, flight.getFlightNo());
			ps.setString(2, flight.getAirlineName());
			ps.setString(3, flight.getSource());
			ps.setString(4, flight.getDestination());
			ps.setInt(5,flight.getNoOfSeats());
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			FlightTime[] flighttimes = flight.getFlightTime();
			for(int i = 0; i< flighttimes.length;i++)
			{
				createFlightTime(flight.getFlightId(),flighttimes[i]);
			}
			System.out.println("Create Flight Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public Flight[] searchFlight(String sourceAirport, String destAirport, String day)
	{
		List<Flight> flight_list = new ArrayList<Flight>();
		Flight flight = null;
		Flight flight1 = null;
		Flight[] flights = null;
		String query = "select f1.flightId as fId1, f2.flightId as fId2, f1.flightNo as fNo1, " +
		"f2.flightNo as fNo2, f1.airlineName as airlineName, f1.source as source, " +
		"f2.source as stopover, f2.destination as destination, f1.noOfSeats as noOfSeats " +
		"FROM flight f1 JOIN flight f2 " +
		"ON f1.destination = f2.source " +
		"WHERE f1.source = ? and f2.destination = ?";
		//String query = "select * from flight where source = ? and destination = ?";/*Get all flights*/

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				FlightTime[] flightTimes1 = retriveFlightTimeByDay(rs.getInt("fId1"),day);
				if(flightTimes1 != null)
				{
					flight = new Flight();
					flight.setFlightId(rs.getInt("fId1"));
					flight.setFlightNo(rs.getString("fNo1"));
					flight.setAirlineName(rs.getString("airlineName"));
					flight.setSource(rs.getString("source"));
					flight.setDestination(rs.getString("stopover"));
					flight.setNoOfSeats(rs.getInt("noOfSeats"));
					flight.setFlightTime(flightTimes1);
					
					
					
					FlightTime[] flightTimes2 = retriveFlightTimeByFlightId(rs.getInt("fId2"));
					if(flightTimes2 != null)
					{
						flight1 = new Flight();
						flight1.setFlightId(rs.getInt("fId2"));
						flight1.setFlightNo(rs.getString("fNo2"));
						flight1.setAirlineName(rs.getString("airlineName"));
						flight1.setSource(rs.getString("stopover"));
						flight1.setDestination(rs.getString("destination"));
						flight1.setNoOfSeats(rs.getInt("noOfSeats"));
						flight1.setFlightTime(flightTimes2);
						
						flight_list.add(flight);
						flight_list.add(flight1);
						//TODO need to check the time of second flight
					}
				}

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!flight_list.isEmpty())
		{
			System.out.println("Retrive flights Successful");
			flights = new Flight[flight_list.size()];
			flights = flight_list.toArray(flights);
			pool.closeConn(con);
			return flights;
		}
		pool.closeConn(con);
		return null;
	}

	public Flight[] retriveFlights(/*Get all flights*/)
	{
		List<Flight> flight_list = new ArrayList<Flight>();
		Flight flight = null;
		Flight[] flights = null;

		String query = "select * from flight";/*Get all flights*/

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			//rs = s.executeQuery();
			while (rs.next()) {
				flight = new Flight();
				flight.setFlightId(rs.getInt("flightId"));
				flight.setFlightNo(rs.getString("flightNo"));
				flight.setAirlineName(rs.getString("airlineName"));
				flight.setSource(rs.getString("source"));
				flight.setDestination(rs.getString("destination"));
				flight.setNoOfSeats(rs.getInt("noOfSeats"));

				FlightTime[] flightTimes = retriveFlightTimeByFlightId(rs.getInt("flightId"));
				flight.setFlightTime(flightTimes);

				flight_list.add(flight);

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!flight_list.isEmpty())
		{
			System.out.println("Retrive flights Successful");
			flights = new Flight[flight_list.size()];
			flights = flight_list.toArray(flights);
			pool.closeConn(con);
			return flights;
		}
		pool.closeConn(con);
		return null;
	}

	public Flight retriveFlightsById(/*Get all flights*/Integer flightId)
	{
		if(flightId < 0)
			return null;

		Flight flight = null;

		String query = "select * from flight where flightId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			rs = ps.executeQuery();
			if (rs.next()) {
				flight = new Flight();
				flight.setFlightId(rs.getInt("flightId"));
				flight.setFlightNo(rs.getString("flightNo"));
				flight.setAirlineName(rs.getString("airlineName"));
				flight.setSource(rs.getString("source"));
				flight.setDestination(rs.getString("destination"));
				flight.setNoOfSeats(rs.getInt("noOfSeats"));

				FlightTime[] flightTimes = retriveFlightTimeByFlightId(rs.getInt("flightId"));
				flight.setFlightTime(flightTimes);

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(flight != null)
		{
			System.out.println("Retrive flight by id Successful");
			pool.closeConn(con);
			return flight;
		}
		pool.closeConn(con);
		return null;
	}

	public Flight retriveFlightsByNo(/*Get all flights*/String flightNo)
	{
		if(flightNo == null || flightNo.isEmpty() || flightNo == "")
			return null;

		Flight flight = null;

		String query = "select * from flight where flightNo = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, flightNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				flight = new Flight();
				flight.setFlightId(rs.getInt("flightId"));
				flight.setFlightNo(rs.getString("flightNo"));
				flight.setAirlineName(rs.getString("airlineName"));
				flight.setSource(rs.getString("source"));
				flight.setDestination(rs.getString("destination"));
				flight.setNoOfSeats(rs.getInt("noOfSeats"));

				FlightTime[] flightTimes = retriveFlightTimeByFlightId(rs.getInt("flightId"));
				flight.setFlightTime(flightTimes);

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(flight != null)
		{
			System.out.println("Retrive FlightbyNo Successful");
			pool.closeConn(con);
			return flight;
		}
		pool.closeConn(con);
		return null;
	}

	public boolean updateflight(Flight flight)
	{
		if(flight == null)
			return false;

		int rc = 0;

		Integer flightId = flight.getFlightId();
		String flightNo = flight.getFlightNo();
		String airlineName = flight.getAirlineName();
		String source = flight.getSource();
		String destination = flight.getDestination();
		Integer noOfSeats = flight.getNoOfSeats();
		FlightTime[] flightTimes = flight.getFlightTime();



		String query = "update flight set " +
		"flightNo = ? ," +
		"airlineName = ? ," +
		"source = ? ," +
		"destination = ? ," +
		"noOfSeats = ? " +
		"where flightId = ?";


		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, flightNo);
			ps.setString(2, airlineName);
			ps.setString(3, source);
			ps.setString(4, destination);
			ps.setInt(5, noOfSeats);
			ps.setInt(6, flightId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			updateFlightTimes(flightTimes);
			System.out.println("Update flight Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public boolean deleteFlight(Integer flightId)
	{
		if(flightId < 0)
			return false;

		int rc = 0;

		String query = "delete from flight where flightId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete flight Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}




	public boolean createFlightTime(Integer flightId,FlightTime flightTime)
	{
		if(flightId < 0 || flightTime == null)
			return false;

		int rc = 0;
		/*										1		2			3	*/
		String query = "insert into flight(flightId ,flightDay,flightTime) " +
		"values (?,?,?)";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			ps.setString(2, flightTime.getFlightDay());
			ps.setString(3, flightTime.getFlightTime());
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create FlightTime Successful");
			pool.closeConn(con);
			return true;
		}
		pool.closeConn(con);
		return false;
	}

	public FlightTime[] retriveFlightTimeByFlightId(/*Get times by flight Id*/Integer flightId)
	{
		if(flightId < 0)
			return null;

		List<FlightTime> flightTime_list = new ArrayList<FlightTime>();
		FlightTime flightTime = null;
		FlightTime[] flightTimes = null;

		String query = "select * from flighttime where flightId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			rs = ps.executeQuery();
			while (rs.next()) {
				flightTime = new FlightTime();
				flightTime.setFlightDay(rs.getString("flightDay"));
				flightTime.setFlightTime(rs.getString("flightTime"));

				flightTime_list.add(flightTime);

			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!flightTime_list.isEmpty())
		{
			System.out.println("Retrive flighttimebyFlightId Successful");
			flightTimes = new FlightTime[flightTime_list.size()];
			flightTimes = flightTime_list.toArray(flightTimes);
			pool.closeConn(con);
			return flightTimes;
		}
		pool.closeConn(con);
		return null;
	}
	//TODO
	public boolean updateFlightTimes(FlightTime[] flightTimes)
	{
		if(flightTimes == null)
			return false;

		return false;
	}

	//TODO
	public boolean deleteFlightTimes(Integer flightId)
	{
		if(flightId < 0)
			return false;

		return false;
	}



	public boolean createJourney(Integer reservationId,Journey journey,Connection con)
	{
		if(reservationId < 0 || journey == null)
			return false;

		int rc = 0;
		/*										1		2			3			4			5	*/
		String query = "insert into journey(FlightId ,boarding,destination,ReservationID,datetime) " +
		"values (?,?,?,?,?)";


		try 
		{
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, journey.getFlightId());
			ps.setString(2, journey.getSource());
			ps.setString(3, journey.getDestination());
			ps.setInt(4, reservationId);
			ps.setString(5, journey.getDateTime());
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create location Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}


	public Journey[] retriveJourney(/*Reservation ID*/Integer reservationId)
	{
		if(reservationId < 0 )
			return null;

		List<Journey> journeyList = new ArrayList<Journey>();
		Journey journeyItem = null;


		Journey[] journey = null;

		String query = "select * from journey where locationId = ?";

		try 
		{
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return null;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rs = ps.executeQuery();
			while(rs.next()) {
				journeyItem = new Journey();
				journeyItem.setFlightId(rs.getInt("flightId"));
				journeyItem.setSource(rs.getString("source"));
				journeyItem.setDestination(rs.getString("destination"));

				journeyList.add(journeyItem);
				//i++;
			}
		}
		catch (SQLException sqle) 
		{
			pool.closeConn(con);
			sqle.printStackTrace();
		}

		if(!journeyList.isEmpty())
		{
			journey = new Journey[journeyList.size()];
			journey = journeyList.toArray(journey);
			System.out.println("Retrive journey Successful");
			pool.closeConn(con);
			return journey;
		}
		pool.closeConn(con);
		return null;
	}

	public boolean updateJourney(Integer reservationId,Journey[] journey,Connection con)
	{
		if(reservationId < 0)
			return false;

		int rc = 0;

		//try 
		//{
		/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
		 */
		if(deleteJourney(reservationId,con))
		{
			for(int j = 0; j<journey.length;j++)
			{
				if(!createJourney(reservationId,journey[j],con))

				{
					try {
						System.err.print("Transaction is being rolled back");
						con.rollback();
						pool.closeConn(con);
						return false;
					} catch(SQLException excep) {
						excep.printStackTrace();
					}
				}
			}
		}
		else
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
		}

		//String query = "update from Journey where reservationId = ?";


		//PreparedStatement ps = con.prepareStatement(query);
		//ps.setInt(1, reservationId);
		//rc = ps.executeUpdate(query);
		/*} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}*/

		if (rc > 0) {
			System.out.println("Delete Journey Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}

	public boolean deleteJourney(Integer reservationId,Connection con)
	{
		if(reservationId < 0)
			return false;

		int rc = 0;

		String query = "delete from Journey where reservationId = ?";

		try 
		{
			/*
			con = pool.getConn();
			if(con == null || con.isClosed())
			{
				System.out.println("No connection to DB");
				return false;
			}
			 */
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rc = ps.executeUpdate();
		} 
		catch (SQLException sqle) 
		{
			try {
				System.err.print("Transaction is being rolled back");
				con.rollback();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
			//pool.closeConn(con);
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete Journey Successful");
			//pool.closeConn(con);
			return true;
		}
		//pool.closeConn(con);
		return false;
	}


}



