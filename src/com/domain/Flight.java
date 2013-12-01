package com.domain;


public class Flight {
	private Integer flightId;
	private String flightNo;
	private String airlineName;
	private String source;
	private String destination;
	private Integer noOfSeats;
	private FlightTime[] flightTime;
	private Journey journey;
	//private Reservation reservation;  //deleted by Amit 26/11/2013
	
	public Integer getFlightId() {
		return flightId;
	}
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Integer getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(Integer noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	public Journey getJourney() {
		return journey;
	}
	public void setJourney(Journey journey) {
		this.journey = journey;
	}
	public FlightTime[] getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(FlightTime[] flightTime) {
		this.flightTime = flightTime;
	}

}
