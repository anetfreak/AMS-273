package com.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.domain.FlightTime;

public class CacheServer {
	private static CacheServer cacheServer = new CacheServer();
	private Map<Integer,FlightTime> flight_time = new HashMap<Integer,FlightTime>();
	private static final int MAX_SIZE = 100;
	private CacheServer()
	{
		
	}
	public static CacheServer getCache()
	{
		return cacheServer;
	}
	
	public boolean addFlightTimeToCache(Integer flightNo,FlightTime ft)
	{
		if(flightNo < 0 || ft == null)
			return false;
		if(flight_time.size() == MAX_SIZE)
		{
			Set<Integer> keys = flight_time.keySet();
			Iterator iter = keys.iterator();
			//removing 10% elements from Map
			int i = 0;
			while(iter.hasNext() && i < (MAX_SIZE/10))
			{
				flight_time.remove(iter.next());
				i++;
			}
		}
		flight_time.put(flightNo, ft);
		return true;
	}
	
	public FlightTime getFlightTimefrmCache(Integer flightNo)
	{
		FlightTime ft = null;
		ft = flight_time.get(flightNo);
		return ft;
	}
	
	public boolean deleteFlightTimefrmCache(Integer flightNo)
	{
		flight_time.remove(flightNo);
		return true;
	}
}
