package com.dao;
/*Using Prepared Statement, Currently not working*/
import java.sql.Connection;
import java.sql.DriverManager;
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
import com.domain.Reservation;
import com.domain.Traveller;


public class PDBConnection {
	Connection con = null;
	Statement s = null;
	ResultSet rs = null;

	public PDBConnection()
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
		/*									1			2		3		4		5	  6	   7	8		  9		10		  		*/
		String query = "insert into person(firstName,lastName,address,city,state,zip,personType,DOB,username,password) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try 
		{	
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

		String query = "select * from person where personId = ?";
		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery(query);
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
			
			rc = ps.executeUpdate(query);
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

		String query = "delete from person where personId = ?";

		try 
		{	
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(11, personId);
			rc = ps.executeUpdate(query);
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
			/*										1			2		3				4	*/
			String query = "insert into customer(customerId,personId,passportNumber,nationality) " +
			"values (?, ?, ?, ?)";
			


			try 
			{
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, customer.getCustomerId());
				ps.setInt(2, personId);
				ps.setString(3, customer.getPassportNumber());
				ps.setString(4, customer.getNationality());
				
				rc = ps.executeUpdate();
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

		String query = "select * from customer where customerId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId);
			rs = ps.executeQuery(query);
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
			"passportNumber = ? ," +		//1
			"nationality = ? " +			//2
			"where customerId = ?";			//3

			try 
			{
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, passportNumber);
				ps.setString(2, nationality);
				ps.setInt(3, customerId);
				
				rc = ps.executeUpdate(query);
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

		String query = "delete from customer where customerId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId);
			rc = ps.executeUpdate(query);
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
			/*										1			2		3		4		5	*/
			String query = "insert into employee(employeeId,personId,workDesc,position,hireDate) " +
			"values (?, ?, ?, ?, ?)";

			try 
			{
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, employee.getEmployeeId());
				ps.setInt(2, personId);
				ps.setString(3, employee.getWorkDesc());
				ps.setInt(4, employee.getPosition());
				ps.setString(5,employee.getHireDate());
				rc = ps.executeUpdate();
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

		String query = "select * from employee where employeeId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rs = ps.executeQuery(query);
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
			"workDesc = ? ," +			//1
			"position = ? ," +			//2
			"hireDate = ? " +			//3
			"where employeeId = ?";		//4

			try 
			{
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, workDesc);
				ps.setInt(2, position);
				ps.setString(3, hireDate);
				ps.setInt(4, employeeId);
				rc = ps.executeUpdate(query);
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

		String query = "delete from customer where employeeId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, employeeId);
			rc = ps.executeUpdate(query);
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
		/*											1			2				3			4					*/
		String query = "insert into reservation(reservationNo,customerId,reservationStatus,seatsBooked) " +
		"values (?,?,?,?)";


		try 
		{
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

		String query = "select * from reservation where reservationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId); 
			
			rs = ps.executeQuery(query);
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

		String query = "select * from reservation where customerId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId); 
			
			rs = ps.executeQuery(query);
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

		String query = "select * from reservation where reservationNo = ?" ;

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, reservationNo); 
			rs = ps.executeQuery(query);
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
		"reservationNo = ? ," +
		"customerId = ? ," +
		"reservationStatus ? ," +
		"seatsBooked = ? " +
		"where reservationId ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, reservationNo);
			ps.setInt(2, customerId);
			ps.setInt(3, reservationStatus);
			ps.setInt(4, seatsBooked);
			ps.setInt(5, reservationId);
			
			rc = ps.executeUpdate(query);
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
		String query = "delete from reservation where reservationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rc = ps.executeUpdate(query);
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

		String query = "delete from reservation where customerId = ?";

		//TODO deleteTravellers(reservationId); //1. get reservation id 2. delete travellers and journey 3. delete reservation
		//TODO deleteJourney();
		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, customerId);
			rc = ps.executeUpdate(query);
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
		/*											1		2			3	  4	  		*/
		String query = "insert into traveller(firstName,lastName,age,sex,reservationId) " +
		"values (?,?,?,?,?)";


		try 
		{
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
		String query = "select * from reservation where reservationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId); 
			rs = ps.executeQuery(query);
			
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
		"firstName = ? ," +
		"lastName = ? ," +
		"age = ? ," +
		"sex = ? " +
		"where travellerId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,firstName);
			ps.setString(2, lastName);
			ps.setInt(3, age);
			ps.setString(4, sex);
			ps.setInt(1, travellerId); 
			rc = ps.executeUpdate(query);
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

		String query = "delete from traveller where reservationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rc = ps.executeUpdate(query);
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

		String query = "delete from traveller where travellerId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, travellerId);
			rc = ps.executeUpdate(query);
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
		/*										1		  2		  3			4	*/
		String query = "insert into location(locationId,state,stateCode,airportCode) " +
		"values (?,?,?,?)";


		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, location.getLocationId());
			ps.setString(2, location.getState());
			ps.setString(3, location.getStateCode());
			ps.setString(4, location.getAirportCode());
			rc = ps.executeUpdate();
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

		String query = "select * from location where locationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, locationId);
			rs = ps.executeQuery(query);
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

		String query = "select * from location where stateCode = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, stateCode);
			rs = ps.executeQuery(query);
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

		String query = "select * from location where state = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, state);
			rs = ps.executeQuery(query);
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

		String query = "select * from location where airportCode = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, airportCode);
			rs = ps.executeQuery(query);
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
		"state = ? ," +				//1
		"stateCode = ? ," +			//2
		"airportCode = ? " +		//3
		"where locationId = ?";		//4

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, state);
			ps.setString(2, stateCode);
			ps.setString(3, airportCode);
			ps.setInt(4, locationId);
			rc = ps.executeUpdate(query);
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

		String query = "delete from location where locationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, locationId);
			rc = ps.executeUpdate(query);
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
		/*										1		2			3		4		5				*/
		String query = "insert into flight(flightNo,airlineName,source,destination,noOfSeats) " +
		"values (?,?,?,?,?,?)";


		try 
		{
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

		String query = "select * from flight where flightId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			rs = ps.executeQuery(query);
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

		String query = "select * from flight where flightNo = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, flightNo);
			rs = ps.executeQuery(query);
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
		"flightNo = ? ," +
		"airlineName = ? ," +
		"source = ? ," +
		"destination = ? ," +
		"noOfSeats = ? " +
		"where flightId = ?";


		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, flightNo);
			ps.setString(2, airlineName);
			ps.setString(3, source);
			ps.setString(4, destination);
			ps.setInt(5, noOfSeats);
			ps.setInt(6, flightId);
			rc = ps.executeUpdate(query);
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

		String query = "delete from flight where flightId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			rc = ps.executeUpdate(query);
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
		/*										1		2			3	*/
		String query = "insert into flight(flightId ,flightDay,flightTime) " +
		"values (?,?,?)";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			ps.setString(2, flightTime.getFlightDay());
			ps.setString(3, flightTime.getFlightTime());
			rc = ps.executeUpdate();
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

		String query = "select * from flighttime where flightId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, flightId);
			rs = ps.executeQuery(query);
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
		/*										1		2			3			4			5	*/
		String query = "insert into journey(FlightId ,boarding,destination,ReservationID,datetime) " +
		"values (?,?,?,?,?)";


		try 
		{
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

		String query = "select * from journey where locationId = ?";

		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, reservationId);
			rs = ps.executeQuery(query);
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
