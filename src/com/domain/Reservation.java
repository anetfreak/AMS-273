package com.domain;


public class Reservation {
	private Integer reservationId;
	private String reservationNo;
	private Integer customerId;	//Added by Amit 26/11/2013
	private Integer reservationStatus;
	//private Integer flightId; //Deleted by Amit 26/11/2013
	private Integer seatsBooked;
	private Traveller[] travellers;
	private Journey[] journey; 
	
	public Integer getReservationId() {
		return reservationId;
	}
	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}
	public String getReservationNo() {
		return reservationNo;
	}
	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
	}
	public Integer getSeatsBooked() {
		return seatsBooked;
	}
	public void setSeatsBooked(Integer seatsBooked) {
		this.seatsBooked = seatsBooked;
	}
	public Integer getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(Integer reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public Traveller[] getTravellers() {
		return travellers;
	}
	public void setTravellers(Traveller[] travellers) {
		this.travellers = travellers;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setJourney(Journey[] journey) {
		this.journey = journey;
	}
	public Journey[] getJourney() {
		return journey;
	}

}
