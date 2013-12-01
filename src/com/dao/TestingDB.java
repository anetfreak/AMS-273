package com.dao;

import java.util.Random;

import com.domain.Customer;
import com.domain.Employee;
import com.domain.Journey;
import com.domain.Person;
import com.domain.Reservation;
import com.domain.Traveller;

public class TestingDB {

int customerId = 0;
	public static void main(String[] args) {

		DBConnection dbcon = new DBConnection();
		TestingDB t = new TestingDB();
		
		
		Customer customer = t.createCustomer();
		if(dbcon.createCustomer(customer))
			System.out.println("Create Customer Success");
		
		
		
		Employee emp = t.createEmployee();
		if(dbcon.createEmployee(emp))
			System.out.println("Create Employee Success");
		
		
		
		Reservation res = t.createReservation();
		if(dbcon.createReservation(res))
			System.out.println("Create reservation Success");
		
	}
	
	
	public Person createPerson()
	{
		Person person = new Person();
		person.setPersonId(123456);
		person.setFirstName("Amit");
		person.setLastName("Agrawal");
		person.setAddress("Metro Station");
		person.setCity("San Jose");
		person.setState("CA");
		person.setPersonType(1);
		person.setZip(95112);
		person.setUsername("username");
		person.setPassword("password");
		person.setDOB("101010");
		
		return person;
	}
	
	public Employee createEmployee()
	{
		Employee employee = new Employee();
		Random rnd = new Random();
		int i = rnd.nextInt();
		employee.setEmployeeId(i);
		employee.setHireDate("10102010");
		employee.setPosition(2);
		employee.setWorkDesc("Manager");
		employee.setPerson(createPerson());
		return employee;
	}
	
	public Customer createCustomer()
	{
		Customer customer = new Customer();
		Random rnd = new Random();
		customerId = rnd.nextInt();
		customer.setCustomerId(customerId);
		customer.setNationality("Indian");
		customer.setPassportNumber("AB1234JP");
		customer.setPerson(createPerson());
		//customer.setReservation(null);
		return customer;
	}
	
	public Reservation createReservation()
	{
			Reservation reservation = new Reservation();
			Random rnd = new Random();
			//int i = rnd.nextInt();
			reservation.setCustomerId(customerId);
			//reservation.setReservationId(i);
			reservation.setReservationNo("123456789");
			reservation.setReservationStatus(1);
			reservation.setSeatsBooked(3);
			
			
			reservation.setJourney(createJourney());
			reservation.setTravellers(createTraveller());
			return reservation;
	}
	
	public Journey[] createJourney()
	{
		Journey[] journey = new Journey[2];
		Random rnd = new Random();
		int j = rnd.nextInt();
		for(int i=0;i<2;i++)
		{
			journey[i] = new Journey();
			journey[i].setFlightId(j);
			journey[i].setDestination("dest");
			journey[i].setSource("source");
			journey[i].setDateTime("101010");
		}
		return journey;
	}
	
	public Traveller[] createTraveller()
	{
		Traveller[] traveller = new Traveller[2];
		Random rnd = new Random();
		int j = rnd.nextInt();
		for(int i=0;i<2;i++)
		{
			traveller[i] = new Traveller();
			traveller[i].setTravellerId(j);
			traveller[i].setFirstName("Amit");
			traveller[i].setLastName("Agrawal");
			traveller[i].setSex("M");
			traveller[i].setAge(26);
			
		}
		return traveller;
	}

}
