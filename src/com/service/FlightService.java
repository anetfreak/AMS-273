package com.service;

import javax.jws.WebService;

import com.dao.PDBConnection;
import com.domain.Flight;

@WebService
public class FlightService {
	
	PDBConnection dbcon = null;
	public FlightService()
	{
		dbcon = new PDBConnection();
	}
	public boolean insertFlight(Flight flight) {
		if(dbcon.createFlight(flight))
		{
			System.out.println("Create flight Success");
			return true;
		}
		return false;
	}
	
	public Flight[] getFlights() {
		return dbcon.retriveFlights();
	}
	
	public Flight getFlightByNo(String flightNo) {
		return dbcon.retriveFlightsByNo(flightNo);
	}
	
	public Flight getFlightById(Integer flightId) {
		return dbcon.retriveFlightsById(flightId);
	}
	
	public Flight searchFlight(String sourceAirport, String destAirport, String departDate, String returnDate) {
		//TODO
		return null;
	}
	
	public boolean updateFlight(Flight flight) {
		if(dbcon.updateflight(flight))
		{
			System.out.println("Update Flight Success");
			return true;
		}
		return false;
	}
	
}
