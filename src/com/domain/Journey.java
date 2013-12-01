package com.domain;

public class Journey {
	
	private String source;
	private String destination;
	private Integer flightId; //added by amit 26/11/2013
	private String dateTime;
	//private Reservation reservation;
	
	
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
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
	public Integer getFlightId() {
		return flightId;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDateTime() {
		return dateTime;
	}
}
