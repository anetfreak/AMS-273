package com.domain;

public class SearchFlight {

	public Flight flight1 = null;
	public Flight flight2 = null;
	
	public boolean add(Flight f1, Flight f2)
	{
		flight1 = f1;
		flight2 = f2;
		return false;
	}
	
	public Flight getFlight1()
	{
		return flight1;
	}
	public Flight getFlight2()
	{
		return flight2;
	}
}
