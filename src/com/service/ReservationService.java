package com.service;

import javax.jws.WebService;

import com.domain.Reservation;

@WebService
public class ReservationService {
	
	public boolean insertReservation(Reservation reservation) {
		return false;
	}
	
	public Reservation getReservation(Integer reservationId) {
		return null;
	}

	public boolean updateReservation(Reservation reservation) {
		return false;
	}
	
}
