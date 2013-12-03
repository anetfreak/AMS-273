package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
import com.domain.Reservation;
import com.domain.Traveller;

public class DBConnection {
	Connection con = null;
	Statement s = null;
	ResultSet rs = null;

	public DBConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/ams_schema",
					"root", "root");

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
	}

	public Integer createPerson(Person person)
	{
		int rc = 0;
		int personId = 0;
		String query = "insert into person(personId,firstName,lastName,address,city,state,zip,personType,DOB,username,password) " +
		"values(" +
		null			//as this field is auto increment
		+ ",'"
		+ person.getFirstName()
		+ "','"
		+ person.getLastName()
		+ "','"
		+ person.getAddress()
		+ "','"
		+ person.getCity()
		+ "','"
		+ person.getState()
		+ "',"
		+ person.getZip()
		+ ","
		+ person.getPersonType()
		+ ",'"
		+ person.getDOB()
		+ "','"
		+ person.getUsername()
		+ "','"
		+ person.getPassword()
		+ "')";
		try 
		{	
			rc = s.executeUpdate(query);

			rs = s.getGeneratedKeys();
			if(rs.next())
				personId = rs.getInt(1);
			System.out.println("Person id = " + personId);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create Person Successful");
			return personId;
		}

		return -1;	
	}

	public Person retrivePerson(Integer personId)
	{
		Person person = null;

		String query = "select * from person where personId = " + "'"
		+ personId + "'";

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(person != null)
		{
			System.out.println("Retrive Person Successful");
			return person;
		}
		return null;
	}

	public boolean updatePerson(Person person)
	{
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
		"firstName = '" +  firstName + "'," +
		"lastName = '" + lastName + "'," +
		"address = '" + address + "'," +
		"city = '" + city + "'," +
		"state = '" + state + "'," +
		"zip = " + zip + "," +
		"personType = " +  personType + "," +
		"dob = '" + dob + "'," +
		"username = '" +  username + "'," +
		"password = '" +  password + "' " +
		"where personID = " + personID;


		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Update Person Successful");
			return true;
		}
		return false;
	}

	public boolean deletePerson(Integer personId)
	{
		int rc = 0;

		String query = "delete from person where personId = " + personId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete Person Successful");
			return true;
		}

		return false;	
	}


	public boolean createCustomer(Customer customer)
	{
		int rc = 0;

		int personId = createPerson(customer.getPerson());
		if(personId > 0)
		{
			String query = "insert into customer(customerId,personId,passportNumber,nationality) " +
			"values ("
			+ customer.getCustomerId()
			+ ","
			+ personId
			+ ",'"
			+ customer.getPassportNumber()
			+ "','"
			+ customer.getNationality()
			+ "')";


			try 
			{
				rc = s.executeUpdate(query);
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
			}

			//createReservation(customer.getCustomerId(),customer.getReservation());
		}
		if (rc > 0) {
			System.out.println("Create Customer Successful");
			return true;
		}

		return false;
	}

	public Customer retriveCustomer(Integer customerId)
	{
		Customer customer = null;

		String query = "select * from customer where customerId = " + "'"
		+ customerId + "'";

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(customer != null)
		{
			System.out.println("Retrive customer Successful");
			return customer;
		}
		return null;
	}

	public boolean updateCustomer(Customer customer)
	{
		int rc = 0;

		Integer customerId = customer.getCustomerId();
		String passportNumber = customer.getPassportNumber();
		String nationality = customer.getNationality();

		if(updatePerson(customer.getPerson()))
		{
			String query = "update customer set " +
			"passportNumber = '" +  passportNumber + "'," +
			"nationality = '" + nationality + "' " +
			"where customerId = " + customerId;

			try 
			{
				rc = s.executeUpdate(query);
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
			}

			//updateReservation(customer.getReservation());
		}

		if (rc > 0) {
			System.out.println("Update Customer Successful");
			return true;
		}

		return false;
	}

	public boolean deleteCustomer(Integer customerId)
	{
		int rc = 0;

		String query = "delete from customer where customerId = " + customerId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			//TODO deletePerson(personId);
			System.out.println("Delete Customer Successful");
			return true;
		}

		return false;	
	}

	public boolean createEmployee(Employee employee) {
		int rc = 0;

		int personId = createPerson(employee.getPerson());
		if(personId > 0)
		{
			String query = "insert into employee(employeeId,personId,workDesc,position,hireDate) " +
			"values ("
			+ employee.getEmployeeId()
			+ ","
			+ personId
			+ ",'"
			+ employee.getWorkDesc()
			+ "',"
			+ employee.getPosition()
			+ ",'"
			+ employee.getHireDate()
			+ "')";

			try 
			{
				rc = s.executeUpdate(query);
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
			}
		}

		if (rc > 0) {
			System.out.println("Create Employee Successful");
			return true;
		}

		return false;
	}

	public Employee retriveEmployee(Integer employeeId)
	{
		Employee employee = null;

		String query = "select * from employee where employeeId = " + "'"
		+ employeeId + "'";

		try 
		{
			rs = s.executeQuery(query);
			if (rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employeeId"));
				employee.setWorkDesc(rs.getString("workDesc"));
				employee.setPosition(rs.getInt("position"));
				employee.setHireDate(rs.getString("hireDate"));

				Person person = retrivePerson(rs.getInt("personId")); 
				employee.setPerson(person);
			}
		}
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if(employee != null)
		{
			System.out.println("Retrive employee Successful");
			return employee;
		}
		return null;
	}

	public boolean updateEmployee(Employee employee)
	{
		int rc = 0;

		Integer employeeId = employee.getEmployeeId();
		String workDesc = employee.getWorkDesc();
		Integer position = employee.getPosition();
		String hireDate = employee.getHireDate();

		if(updatePerson(employee.getPerson()))
		{
			String query = "update employee set " +
			"workDesc = '" +  workDesc + "'," +
			"position = " + position + "," +
			"hireDate = '" +  hireDate + "' " +
			"where employeeId = " + employeeId;

			try 
			{
				rc = s.executeUpdate(query);
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
			}
		}

		if (rc > 0) {
			System.out.println("Update Employee Successful");
			return true;
		}

		return false;
	}

	public boolean deleteEmployee(Integer employeeId)
	{
		int rc = 0;

		String query = "delete from customer where employeeId = " + employeeId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete employee Successful");
			return true;
		}

		return false;	
	}





	public boolean createReservation(Reservation reservation)
	{
		int rc = 0;
		int reservationId = -1;
		String query = "insert into reservation(reservationId,reservationNo,customerId,reservationStatus,seatsBooked) " +
		"values ("
		+ null
		+ ",'"
		+ reservation.getReservationNo()
		+ "',"
		+ reservation.getCustomerId() 
		+ ","
		+ reservation.getReservationStatus()
		//+ ","
		//+ reservation.getFlightId()
		+ ","
		+ reservation.getSeatsBooked()
		+ ")";


		try 
		{
			rc = s.executeUpdate(query);
			rs = s.getGeneratedKeys();
			if(rs.next())
				reservationId = rs.getInt(1);
			System.out.println("reservationId = " + reservationId);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		Traveller[] travellers =  reservation.getTravellers();
		for(int i = 0; i< travellers.length;i++)
			createTravller(reservationId,travellers[i]);	

		Journey[] journey = reservation.getJourney();
		for(int j = 0; j<journey.length;j++)
			createJourney(reservationId,journey[j]);

		if (rc > 0) {
			System.out.println("Create reservation Successful");
			return true;
		}
		return false;
	}

	//retrive by reservationId
	public Reservation retriveReservationbyResId(Integer reservationId)
	{
		Reservation reservation = null;

		String query = "select * from reservation where reservationId = " + "'"
		+ reservationId + "'";

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(reservation != null)
		{
			System.out.println("Retrive reservation Successful");
			return reservation;
		}
		return null;
	}

	//retrive by customer Id
	public Reservation retriveReservationByCustId(Integer customerId)
	{
		Reservation reservation = null;

		String query = "select * from reservation where customerId = " + customerId;

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(reservation != null)
		{
			System.out.println("Retrive reservation Successful");
			return reservation;
		}
		return null;
	}
	//retrive by reservationNo
	public Reservation retriveReservationbyResNo(String reservationNo)
	{
		Reservation reservation = null;

		String query = "select * from reservation where reservationNo = " + reservationNo ;

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(reservation != null)
		{
			System.out.println("Retrive reservation Successful");
			return reservation;
		}
		return null;
	}

	public boolean updateReservation(Reservation reservation)
	{
		int rc = 0;

		Integer reservationId = reservation.getReservationId();
		String reservationNo = reservation.getReservationNo();
		Integer customerId = reservation.getCustomerId();
		Integer reservationStatus = reservation.getReservationStatus();
		Integer seatsBooked = reservation.getSeatsBooked();
		Traveller[] travellers = reservation.getTravellers();


		String query = "update reservation set " +
		"reservationNo = '" +  reservationNo + "'," +
		"customerId = " + customerId + "," +
		"reservationStatus = " + reservationStatus + "," +
		"seatsBooked = " +  seatsBooked + " " +
		"where reservationId = " + reservationId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		for(int i = 0; i< travellers.length;i++)
			updateTravller(travellers[i]);



		if (rc > 0) {
			System.out.println("Update reservation Successful");
			return true;
		}
		return false;
	}

	public boolean deleteReservation(Integer reservationId)
	{
		int rc = 0;

		deleteTravellers(reservationId);
		//TODO deleteJourney();
		String query = "delete from reservation where reservationId = " + reservationId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete reservation Successful");
			return true;
		}
		return false;
	}

	public boolean deleteReservationByCustomer(Integer customerId)
	{
		int rc = 0;

		String query = "delete from reservation where customerId = " + customerId;

		//TODO deleteTravellers(reservationId); //1. get reservation id 2. delete travellers and journey 3. delete reservation
		//TODO deleteJourney();
		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete reservation Successful");
			return true;
		}
		return false;
	}


	public boolean createTravller(Integer reservationId,Traveller traveller)
	{
		int rc = 0;
		String query = "insert into traveller(travellerId,firstName,lastName,age,sex,reservationId) " +
		"values ("
		+ null
		+ ",'"
		+ traveller.getFirstName()
		+ "','"
		+ traveller.getLastName()
		+ "',"
		+ traveller.getAge()
		+ ",'"
		+ traveller.getSex()
		+ "',"
		+ reservationId
		+ ")";


		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create Traveller Successful");
			return true;
		}
		return false;
	}

	public Traveller[] retriveTravellers(Integer reservationId)
	{
		List<Traveller> travellerList = new ArrayList<Traveller>();
		Traveller travelleritem = null;
		Traveller[] travellers = null;
		String query = "select * from reservation where reservationId = " + "'"
		+ reservationId + "'";

		try 
		{
			rs = s.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				travelleritem = new Traveller();

				travelleritem.setTravellerId(rs.getInt("travellerId"));
				travelleritem.setFirstName(rs.getString("firstName"));
				travelleritem.setLastName(rs.getString("lastName"));
				travelleritem.setAge(rs.getInt("age"));
				travelleritem.setSex(rs.getString("sex"));

				travellerList.add(travelleritem);
				i++;
			}
		}
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if(!travellerList.isEmpty())
		{
			travellers = new Traveller[travellerList.size()];
			travellers = travellerList.toArray(travellers);

			System.out.println("Retrive traveller Successful");
			return travellers;
		}

		return null;
	}


	public boolean updateTravller(Traveller traveller)
	{
		int rc = 0;

		Integer travellerId = traveller.getTravellerId();
		String firstName = traveller.getFirstName();
		String lastName = traveller.getLastName();
		Integer age = traveller.getAge();
		String sex = traveller.getSex();
		//TODO need to check if need update reservationId

		String query = "update traveller set " +
		"firstName = '" + firstName + "'," +
		"lastName = '" + lastName + "'," +
		"age = " + age + "," +
		"sex = '" +  sex + "' " +
		"where travellerId = " + travellerId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Update traveller Successful");
			return true;
		}
		return false;
	}

	//Delete on the basis of reservationId
	public boolean deleteTravellers(Integer reservationId)
	{
		int rc = 0;

		String query = "delete from traveller where reservationId = " + reservationId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete travellers Successful");
			return true;
		}
		return false;
	}

	//Delete on the basis of travellerId
	public boolean deleteTraveller(Integer travellerId)
	{
		int rc = 0;

		String query = "delete from traveller where travellerId = " + travellerId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete traveller Successful");
			return true;
		}
		return false;
	}


	public boolean createLocation(Location location)
	{
		int rc = 0;
		String query = "insert into location(locationId,state,stateCode,airportCode) " +
		"values ("
		+ location.getLocationId()
		+ ",'"
		+ location.getState()
		+ "','"
		+ location.getStateCode()
		+ "','"
		+ location.getAirportCode()
		+ "')";


		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create location Successful");
			return true;
		}
		return false;
	}

	public Location retriveLocation(Integer locationId)
	{
		Location location = null;

		String query = "select * from location where locationId = " + locationId;

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			return location;
		}
		return null;
	}

	public Location retriveLocationByStateCode(String stateCode)
	{
		Location location = null;

		String query = "select * from location where stateCode = '" + stateCode + "'";

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			return location;
		}
		return null;
	}

	public Location retriveLocationByState(String state)
	{
		Location location = null;

		String query = "select * from location where state = '" + state + "'";

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			return location;
		}
		return null;
	}

	public Location retriveLocationByAirportCode(String airportCode)
	{
		Location location = null;

		String query = "select * from location where airportCode = '" + airportCode + "'";

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(location != null)
		{
			System.out.println("Retrive location Successful");
			return location;
		}
		return null;
	}

	public boolean updateLocation(Location location)
	{
		int rc = 0;

		Integer locationId = location.getLocationId();
		String state = location.getState();
		String stateCode = location.getStateCode();
		String airportCode = location.getAirportCode();


		String query = "update location set " +
		"state = '" + state + "'," +
		"stateCode = '" + stateCode + "'," +
		"airportCode = '" +  airportCode + "' " +
		"where locationId = " + locationId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Update location Successful");
			return true;
		}
		return false;
	}

	public boolean deleteLocation(Integer locationId)
	{
		int rc = 0;

		String query = "delete from location where locationId = " + locationId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete location Successful");
			return true;
		}
		return false;
	}

	public boolean createFlight(Flight flight)
	{
		int rc = 0;
		String query = "insert into flight(flightId ,flightNo,airlineName,source,destination,noOfSeats) " +
		"values ("
		+ flight.getFlightId()
		+ ",'"
		+ flight.getFlightNo()
		+ "','"
		+ flight.getAirlineName()
		+ "','"
		+ flight.getSource()
		+ "','"
		+ flight.getDestination()
		+ "',"
		+ flight.getNoOfSeats()
		+ ")";


		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			FlightTime[] flighttimes = flight.getFlightTime();
			for(int i = 0; i< flighttimes.length;i++)
			{
				createFlightTime(flight.getFlightId(),flighttimes[i]);
			}
			System.out.println("Create Flight Successful");
			return true;
		}
		return false;
	}

	public Flight[] retriveFlights(/*Get all flights*/)
	{
		List<Flight> flight_list = new ArrayList<Flight>();
		Flight flight = null;
		Flight[] flights = null;

		String query = "select * from flight";/*Get all flights*/

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(!flight_list.isEmpty())
		{
			System.out.println("Retrive flights Successful");
			flights = new Flight[flight_list.size()];
			flights = flight_list.toArray(flights);
			return flights;
		}
		return null;
	}

	public Flight retriveFlightsById(/*Get all flights*/Integer flightId)
	{
		Flight flight = null;

		String query = "select * from flight where flightId = " + flightId;

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(flight != null)
		{
			System.out.println("Retrive flight by id Successful");
			return flight;
		}
		return null;
	}

	public Flight retriveFlightsByNo(/*Get all flights*/String flightNo)
	{
		Flight flight = null;

		String query = "select * from flight where flightNo = " + flightNo;

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(flight != null)
		{
			System.out.println("Retrive FlightbyNo Successful");
			return flight;
		}
		return null;
	}

	public boolean updateflight(Flight flight)
	{
		int rc = 0;

		Integer flightId = flight.getFlightId();
		String flightNo = flight.getFlightNo();
		String airlineName = flight.getAirlineName();
		String source = flight.getSource();
		String destination = flight.getDestination();
		Integer noOfSeats = flight.getNoOfSeats();
		FlightTime[] flightTimes = flight.getFlightTime();



		String query = "update flight set " +
		"flightNo = '" + flightNo + "'," +
		"airlineName = '" + airlineName + "'," +
		"source = '" +  source + "'," +
		"destination = '" +  destination + "'," +
		"noOfSeats = " +  noOfSeats + " " +
		"where flightId = " + flightId;


		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			updateFlightTimes(flightTimes);
			System.out.println("Update flight Successful");
			return true;
		}
		return false;
	}

	public boolean deleteFlight(Integer flightId)
	{
		int rc = 0;

		String query = "delete from flight where flightId = " + flightId;

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Delete flight Successful");
			return true;
		}
		return false;
	}




	public boolean createFlightTime(Integer flightId,FlightTime flightTime)
	{
		int rc = 0;
		String query = "insert into flight(flightId ,flightDay,flightTime) " +
		"values ("
		+ flightId
		+ ",'"
		+ flightTime.getFlightDay()
		+ "','"
		+ flightTime.getFlightTime()
		+ ")";

		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create FlightTime Successful");
			return true;
		}
		return false;
	}

	public FlightTime[] retriveFlightTimeByFlightId(/*Get times by flight Id*/Integer flightId)
	{
		List<FlightTime> flightTime_list = new ArrayList<FlightTime>();
		FlightTime flightTime = null;
		FlightTime[] flightTimes = null;

		String query = "select * from flighttime where flightId = " + flightId;

		try 
		{
			rs = s.executeQuery(query);
			while (rs.next()) {
				flightTime = new FlightTime();
				flightTime.setFlightDay(rs.getString("flightDay"));
				flightTime.setFlightTime(rs.getString("flightTime"));

				flightTime_list.add(flightTime);

			}
		}
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if(!flightTime_list.isEmpty())
		{
			System.out.println("Retrive flighttimebyFlightId Successful");
			flightTimes = new FlightTime[flightTime_list.size()];
			flightTimes = flightTime_list.toArray(flightTimes);
			return flightTimes;
		}
		return null;
	}
	//TODO
	public boolean updateFlightTimes(FlightTime[] flightTimes)
	{
		return false;
	}

	//TODO
	public boolean deleteFlightTimes(Integer flightId)
	{
		return false;
	}



	public boolean createJourney(Integer reservationId,Journey journey)
	{
		int rc = 0;
		String query = "insert into journey(FlightId ,boarding,destination,ReservationID,datetime) " +
		"values ("
		+ journey.getFlightId()
		+ ",'"
		+ journey.getSource()
		+ "','"
		+ journey.getDestination()
		+ "',"
		+ reservationId
		+ ",'"
		+ journey.getDateTime()
		+ "')";


		try 
		{
			rc = s.executeUpdate(query);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
		}

		if (rc > 0) {
			System.out.println("Create location Successful");
			return true;
		}
		return false;
	}


	public Journey[] retriveJourney(/*Reservation ID*/Integer reservationId)
	{
		List<Journey> journeyList = new ArrayList<Journey>();
		Journey journeyItem = null;


		Journey[] journey = null;

		String query = "select * from journey where locationId = " + reservationId;

		try 
		{
			rs = s.executeQuery(query);
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
			sqle.printStackTrace();
		}

		if(!journeyList.isEmpty())
		{
			journey = new Journey[journeyList.size()];
			journey = journeyList.toArray(journey);
			System.out.println("Retrive journey Successful");
			return journey;
		}
		return null;
	}





}
