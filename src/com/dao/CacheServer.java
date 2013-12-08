package com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.domain.Flight;
import com.domain.FlightTime;
import com.domain.SearchFlight;

public class CacheServer {
	private static CacheServer cacheServer = new CacheServer();
	private Map<Integer,FlightTime[]> flight_time = new HashMap<Integer,FlightTime[]>();
	private Map<String,SearchFlight> searchFlight = new HashMap<String,SearchFlight>();
	private static final int MAX_SIZE = 1000;
	private CacheServer()
	{

	}
	public static CacheServer getCache()
	{
		return cacheServer;
	}


	public List<SearchFlight> searchFlightfrmCache(String sourceAirport, String destAirport, String day)
	{
		SearchFlight sf = null;
		List<SearchFlight> sfl = new ArrayList<SearchFlight>();
		int index = 0;
		while(true)
		{
			String key = index+"|"+sourceAirport+"|"+destAirport+"|"+day;
			index++;
			sf = searchFlight.get(key);
			if(sf == null)
				break;
			sfl.add(sf);
		}
		return sfl;
	}

	public boolean removeFlightfrmCache(String sourceAirport, String destAirport, String day)
	{
		int index = 0;

		while(true)
		{
			String key = index+"|"+sourceAirport+"|"+destAirport+"|"+day;
			index++;
			if(searchFlight.remove(key)==null)
			{
				return true;
			}
		}
	}

	public boolean addFlightToCache(String sourceAirport, String destAirport, String day,Flight f1,Flight f2, int index)
	{
		SearchFlight sf = new SearchFlight();
		sf.add(f1, f2);
		String key = index+"|"+sourceAirport+"|"+destAirport+"|"+day;

		if(searchFlight.size() == MAX_SIZE)
		{
			Set<String> keys = searchFlight.keySet();
			Iterator iter = keys.iterator();
			//removing 10% elements from Map
			int i = 0;
			while(iter.hasNext() && i < (MAX_SIZE/10))
			{
				SearchFlight removedsf = searchFlight.remove(iter.next());
				int removeindex = 0;
				while(true)
				{
					String removekey = removeindex+"|"+removedsf.flight1.getSource()+"|"+removedsf.flight2.getDestination()+"|"+day;
					index++;
					//if(removekey.equalsIgnoreCase(anotherString))
					int secondChance = 0;
					if(searchFlight.remove(removekey)==null)
					{	
						if(secondChance == 0)
						{
							//give one more chance as one item is already removed, and removing same key will give null
							secondChance = 1;
						}
						else
						{
							break;
						}
					}
					keys = searchFlight.keySet();
					iter = keys.iterator();

				}
				i++;
			}
		}

		searchFlight.put(key,sf);
		return true;
	}

	public boolean addFlightTimeToCache(Integer flightId,FlightTime[] ft)
	{
		if(flightId < 0 || ft == null)
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
		flight_time.put(flightId, ft);
		System.out.println("Added to cache");
		return true;
	}

	public FlightTime[] getFlightTimefrmCache(Integer flightId)
	{
		FlightTime[] ft = null;
		ft = flight_time.get(flightId);
		return ft;
	}

	public boolean deleteFlightTimefrmCache(Integer flightId)
	{
		flight_time.remove(flightId);
		return true;
	}
}
