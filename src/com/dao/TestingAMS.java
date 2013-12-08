package com.dao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.domain.Customer;
import com.domain.Employee;
import com.domain.Flight;
import com.domain.FlightTime;
import com.domain.Person;


public class TestingAMS {

	/*This function generated an array of random numbers which are unique and are between 1 and 5000*/
	public List<Integer> randomNumberGenerator(int startInt, int endInt)
	{
		List<Integer> arrOfInt = new ArrayList<Integer>();
		for (int i = startInt; i < endInt; i++) {
			arrOfInt.add(i);
		}
		//shuffling the values
		Collections.shuffle(arrOfInt);
		return arrOfInt;
	}
	/* this function tests the creation of 
	 * approximately 5000 customers 
	 */
	public boolean testingCustomerCreation()
	{
		List<Integer> arrOfInt = randomNumberGenerator(0,5000);
		List<Customer> custList = new ArrayList<Customer>();
		int customerId;
		PDBConnection dbcon = new PDBConnection();
		for(int i=0 ; i<5000 ; i++)
		{
			customerId = arrOfInt.get(i);
			Customer customer = createCustomer(customerId);
			if(dbcon.createCustomer(customer)!=0)
			{
				custList.add(customer);
			}
		}
		if(custList.size() == 5000)
			return true;
		else 
			return false;
	}

	public Customer createCustomer(int customerId)
	{

		Customer customer = new Customer();

		customer.setCustomerId(customerId);
		customer.setNationality("Indian");
		customer.setPassportNumber("AB1234JP");
		customer.setPerson(createPerson(++customerId));
		//customer.setReservation(null);
		return customer;

	}
	/* this function tests the creation of 
	 * approximately 5000 employees 
	 */
	public boolean testingEmployeeCreation()
	{
		List<Integer> arrOfInt = randomNumberGenerator(5000,10000);
		List<Employee> empList = new ArrayList<Employee>();
		int empId;
		PDBConnection dbcon = new PDBConnection();
		for(int i=0 ; i<5000 ; i++)
		{
			empId = arrOfInt.get(i);

			Employee emp = createEmployee(empId);
			if(dbcon.createEmployee(emp)!=0)
				empList.add(emp);
		}
		if(empList.size() == 5000)
			return true;
		else 
			return false;

	}
	public Employee createEmployee(int employeeId)
	{
		Employee employee = new Employee();

		employee.setEmployeeId(employeeId);
		employee.setHireDate("10102010");
		employee.setPosition("Manager");
		employee.setWorkDesc("Manager");

		employee.setPerson(createPerson(++employeeId));
		return employee;

	}
	public Person createPerson(int personId)
	{
		Person person = new Person();
		person.setPersonId(personId);
		person.setFirstName("Kanika");
		person.setLastName("Anand");
		person.setAddress("Metro Station");
		person.setCity("San Jose");
		person.setState("CA");
		person.setPersonType(1);
		person.setZip(95112);
		person.setUsername("user"+personId+"@gmai.com");
		person.setPassword("password");
		person.setDOB("101010");

		return person;
	}
	/* This function tests the creation of 10,000 flights
	 * 
	 */
	public boolean testingFlightCreation()
	{	
		List<Integer> arrOfInt = randomNumberGenerator(10000,11000);
		Flight flight;
		FlightTime[] flightTime = new FlightTime[3];
		for(int i =0; i<3; i++)
		{
			Random rnd = new Random();
			int r = rnd.nextInt(6);
			String day = "";
			switch(r){
			case 0 : day="sun" ; break;
			case 1 : day="mon" ; break;
			case 2 : day="tue" ; break;
			case 3 : day="wed" ; break;
			case 4 : day="thu" ; break;
			case 5 : day="fri" ; break;
			case 6 : day="sat" ; break;
			}
			flightTime[i] = new FlightTime();
			flightTime[i].setFlightDay(day);
			flightTime[i].setFlightTime("0"+i+":00:00");
		}
		boolean bool = true;
		PDBConnection dbcon = new PDBConnection(); 
		for(int i=0; i<1000; i++){
			flight = new Flight();
			flight.setFlightId(arrOfInt.get(i));
			flight.setFlightNo("testflight");
			flight.setSource("SJC");
			flight.setDestination("AUS");
			flight.setNoOfSeats(100);
			flight.setAirlineName("AMS");
			flight.setFlightTime(flightTime);
			bool = dbcon.createFlight(flight);
		}
		return bool;
	}


	public static void main(String[] args) {

		TestingAMS t = new TestingAMS();
		//create 5000 customers
		/*if(t.testingCustomerCreation())
		{
			System.out.println("5000 customers created");
		}

		//create 5000 employees
		if(t.testingEmployeeCreation())
		{
			System.out.println("5000 employees created");
		}*/
		if(t.testingFlightCreation())
		{
			System.out.println("1000 flights created");
		}

	}

}
