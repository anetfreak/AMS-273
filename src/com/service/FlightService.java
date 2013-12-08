package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jws.WebService;

import com.dao.PDBConnection;
import com.domain.Flight;
import com.domain.Location;

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
			Date date = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(departDate);
			@SuppressWarnings("deprecation")
			int iday = date.getDay();
			System.out.println("iDay is : "+iday);
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
			System.out.println("In Parse Exception");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("In other Exception");
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
	
	public Location[] getLocations()
	{
		return dbcon.retriveLocations();
	}
	
}
