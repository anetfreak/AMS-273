package com.dao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.dao.PDBConnection;
import com.dao.PTestingDB;
import com.domain.Customer;
import com.domain.Employee;
import com.domain.Flight;
import com.domain.FlightTime;
import com.domain.Journey;
import com.domain.Location;
import com.domain.Person;
import com.domain.Reservation;
import com.domain.Traveller;
import com.service.CustomerService;

public class TestingAMS {
	/********* ENTITY CREATION TEST CASES***********/
	
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
		PDBConnection dbcon = new PDBConnection();
		Random rnd = new Random();
		Location[] locations = dbcon.retriveLocations();
		
		List<Integer> arrOfInt = randomNumberGenerator(10000,11000);
		Flight flight;
		
		boolean bool = true;
		
		for(int i=0; i<1000; i++){
			flight = new Flight();
			flight.setFlightId(arrOfInt.get(i));
			flight.setFlightNo("AF"+i);
			
			int lr = rnd.nextInt(locations.length-1);
			flight.setSource(locations[lr].getAirportCode());
			int lr1 = rnd.nextInt(locations.length-1);
			if(lr == lr1)
				lr1 = rnd.nextInt(locations.length-1);
			flight.setDestination(locations[lr1].getAirportCode());
			flight.setNoOfSeats(100);
			flight.setAirlineName("AMS");
			
			FlightTime[] flightTime = new FlightTime[3];
			for(int j =0; j<3; j++)
			{
				
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
				flightTime[j] = new FlightTime();
				flightTime[j].setFlightDay(day);
				flightTime[j].setFlightTime("0"+i+":00:00");
			}
			
			flight.setFlightTime(flightTime);
			bool = dbcon.createFlight(flight);
		}
		return bool;
	}
		public boolean testingReservationCreation()/****** currently not working*/
	{
		List<Integer> arrOfInt = randomNumberGenerator(20000,30000);
		Reservation reservation;
		Traveller []traveller = new Traveller[2];
		PDBConnection dbcon = new PDBConnection(); 
		Journey []journey = new Journey[1];
		int customerId;
	
		int count = 0;
		for(int i=0; i<1; i++)
		{
			//customerId = i-10000;
			reservation = new Reservation();
			reservation.setCustomerId(123121234);
			//reservation.setReservationId(arrOfInt.get(i));
			//reservation.setReservationNo("anynumber");
			reservation.setReservationStatus(1);
			reservation.setSeatsBooked(2);
			
			for(int j=0; j<2; j++)
			{
				traveller[j] = new Traveller();
				traveller[j].setAge(30);
				traveller[j].setFirstName("Kanika");
				traveller[j].setLastName("anand");
				traveller[j].setSex("F");
			}
			
			for(int k=0; k<1; k++)
			{
				journey[k] = new Journey();
				
				journey[k].setSource("SJC");
				journey[k].setDestination("AUS");
				journey[k].setFlightId(k+4438);
				journey[k].setDateTime("09:00:00");
				
			}
			reservation.setTravellers(traveller);
			reservation.setJourney(journey);
			if(dbcon.createReservation(reservation))
				count++;
		}
		if(count == 1000)
			return true;
		else 
			return false;
	}
		/*********** CUSTOMER SERVICE TEST CASES****************/
	public boolean testGetCustomers()
	{
		Customer [] cust_list ;
		PDBConnection dbcon = new PDBConnection();
		cust_list = dbcon.retriveCustomers();
		if(cust_list.length !=0)
		{
			return true;
		}
		return false;
	}
	public boolean testGetCustomer(int customerId)
	{
		PDBConnection dbcon = new PDBConnection();
		Customer customer = dbcon.retriveCustomer(customerId);
		if(customer !=null)
		{
			return true;
		}
		return false;
	}

	public boolean testRetriveCustomerBypId(int personId)
	{
		PDBConnection dbcon = new PDBConnection();
		Customer customer = dbcon.retriveCustomerbypId(personId);
		if(customer !=null)
		{
			return true;
		}
		return false;
	}
	public boolean testUpdateCustomer(Customer customer)
	{
		PDBConnection dbcon = new PDBConnection();
		if(dbcon.updateCustomer(customer))
			return true;
		else 
			return false;
		
	}
	/****************EMPLOEE SERVICE TEST CASES*******************/
	public boolean testGetEmployees()
	{
		Employee [] emp_list ;
		PDBConnection dbcon = new PDBConnection();
		emp_list = dbcon.retriveEmployees();
		if(emp_list.length !=0)
		{
			return true;
		}
		return false;
	}
	public boolean testGetEmployee(int employeeId)
	{
		PDBConnection dbcon = new PDBConnection();
		Employee employee = dbcon.retriveEmployee(employeeId);
		if(employee !=null)
		{
			return true;
		}
		return false;
	}
	
	public boolean testRetriveEmployeeBypId(int personId)
	{
		PDBConnection dbcon = new PDBConnection();
		Employee employee = dbcon.retriveEmployeebypId(personId);
		if(employee !=null)
		{
			return true;
		}
		return false;
	}
	public boolean testUpdateEmployee(Employee employee)
	{
		PDBConnection dbcon = new PDBConnection();
		if(dbcon.updateEmployee(employee))
			return true;
		else 
			return false;
	}
	/*******************FLIGHT SERVICE TEST CASES***************/
	public boolean testGetFlightByNo(String flightNo)
	{
		PDBConnection dbcon = new PDBConnection();
		if(dbcon.retriveFlightsByNo(flightNo) != null)
			return  true;
		else 
			return false;	
	}
	public boolean testGetFlightById(int flightId)
	{
		PDBConnection dbcon = new PDBConnection();
		if(dbcon.retriveFlightsById(flightId) != null)
			return true;
		else 
			return false;
	}
	public boolean testSearchFlight(String sourceAirport, String destAirport, String day){
		PDBConnection dbcon = new PDBConnection();
		Flight []flights = dbcon.searchFlight(sourceAirport, destAirport, day);
		if (flights.length !=0)
			return true;
		else 
			return false;
	}
	public boolean testUpdateFlight(Flight flight)
	{
		PDBConnection dbcon = new PDBConnection();
		if(dbcon.updateflight(flight))
			return true;
		else 
			return false;
	}
	
	/********************* PERSON SERVICE TEST CASES********************/
	public boolean testGetPerson(int personId)
	{
		PDBConnection dbcon = new PDBConnection();
		Person person = dbcon.retrivePerson(personId);
		if(person != null)
		{
			return true;
		}
		else 
			return false;
	} 
	/*public boolean testUpdatePerson(Person person)
	{
		//PDBConnection dbcon = new PDBConnection();
		//if(dbcon.updatePerson(person, dbcon))
			return true;
		//else 
			//return false;
		
	}*/
	
	/******************RESERVATION SERVICE TEST CASES**************/
	public boolean testGetReservations()
	{
		PDBConnection dbcon = new PDBConnection();
		Reservation []reservations = dbcon.retriveReservations();
		if(reservations.length != 0)
			return true;
		else 
			return false;
	}
	
	public boolean testGetReservation(int reservationId)
	{
		PDBConnection dbcon = new PDBConnection();
		Reservation reservation = dbcon.retriveReservationbyResId(reservationId);
		if (reservation != null)
			return true;
		else 
			return false;
	}
	public boolean testUpdateReservtion(Reservation reservation)
	{
		PDBConnection dbcon = new PDBConnection();
		if(dbcon.updateReservation(reservation))
			return true;
		else 
			return false;
	}

	public static void main(String[] args) {

		TestingAMS t = new TestingAMS();
		t.testingReservationCreation();
		//create 5000 customers
		/*if(t.testingCustomerCreation())
		{
			System.out.println("5000 customers created");
		}

		//create 5000 employees
		if(t.testingEmployeeCreation())
		{
			System.out.println("5000 employees created");
		}
		if(t.testingFlightCreation())
		{
			System.out.println("1000 flights created");
		}
	/*	if(t.testingReservationCreation())
		{
			System.out.println("1000 flights created");
		}*/

	}

}
