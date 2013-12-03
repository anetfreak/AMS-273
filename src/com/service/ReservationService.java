package com.service;

import javax.jws.WebService;

import com.dao.PDBConnection;
import com.domain.Reservation;

@WebService
public class ReservationService {
	
	PDBConnection dbcon = null;
	public ReservationService()
	{
		dbcon = new PDBConnection();
	}
	public boolean insertReservation(Reservation reservation) {
		//TODO server side validations
		if(dbcon.createReservation(reservation))
		{
			System.out.println("Create Reservation Success");
			return true;
		}
		return false;
	}
	
	public Reservation getReservation(Integer reservationId) {
		return dbcon.retriveReservationbyResId(reservationId);
	}

	public boolean updateReservation(Reservation reservation) {
		//TODO server side validations
		if(dbcon.updateReservation(reservation))
		{
			System.out.println("Update Reservation Success");
			return true;
		}
		return false;
	}
	
}
