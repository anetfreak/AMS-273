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
	
		public Flight[] searchFlight(String sourceAirport, String destAirport, String departDate) {
		//TODO
		try {
			Date date = new SimpleDateFormat("YYYY-MM-DD",Locale.ENGLISH).parse(departDate);
			@SuppressWarnings("deprecation")
			int iday = date.getDate();
			String day = "";
			switch(iday){
			case 0: day ="sun";break;
			case 1: day ="mon";break;
			case 2: day ="tue";break;
			case 3: day ="wed";break;
			case 4: day ="thu";break;
			case 5: day ="fri";break;
			case 6: day ="sat";break;			
			}
			
			return dbcon.searchFlight(sourceAirport, destAirport, day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
