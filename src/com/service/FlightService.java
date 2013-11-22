package com.service;

import javax.jws.WebService;

import com.domain.Flight;

@WebService
public class FlightService {
	
	
	public boolean insertFlight(Flight flight) {
		return false;
	}
	
	public Flight[] getFlights() {
		return null;
	}
	
	public Flight getFlightByNo(Integer flightNo) {
		return null;
	}
	
	public Flight getFlightById(Integer flightId) {
		return null;
	}
	
	public Flight searchFlight(String sourceAirport, String destAirport, String departDate, String returnDate) {
		return null;
	}
	
	public boolean updateFlight(Flight flight) {
		return false;
	}
	
}
