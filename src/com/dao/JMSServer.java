package com.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.domain.Customer;
import com.domain.Flight;
import com.domain.Journey;
import com.domain.Reservation;


public class JMSServer  {
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private Topic topic;
	PDBConnection dbcon;


		public JMSServer(){
			try
			{
				Properties properties = new Properties();
				properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
				properties.put(Context.URL_PKG_PREFIXES, "org.jnp.interfaces");
				properties.put(Context.PROVIDER_URL, "localhost");

				InitialContext jndi = new InitialContext(properties);
				ConnectionFactory conFactory = (ConnectionFactory)jndi.lookup("XAConnectionFactory");
				connection = conFactory.createConnection();

				session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );

				try
				{
					topic = (Topic)jndi.lookup("FlightTopic");
				}
				catch(NamingException NE1)
				{
					System.out.println("NamingException: "+NE1+ " : Continuing anyway...");
				}

				if( null == topic )
				{
					topic = session.createTopic("FlightTopic");
					jndi.bind("FlightTopic", topic);
				}
				System.out.println("Server started waiting for client requests");
				connection.start();
			}
			catch(NamingException NE)
			{
				System.out.println("Naming Exception: "+NE);
			}
			catch(JMSException JMSE)
			{
				System.out.println("JMS Exception: "+JMSE);
				JMSE.printStackTrace();
			}

		
		}


		public void publishFlightStatus(Customer cust, Reservation res)
		{
			
			int i = 0,k = 0;
			Flight[] flights = null;
			Integer[] flightIds = null;
			List<String> flightTime= new ArrayList<String>();
			String[] flightStatus = null;
			
			Journey[] journeys = res.getJourney();
			while(journeys[k]!=null)
			{
				flightIds[k] = journeys[k].getFlightId();
				
			}
			dbcon = new PDBConnection();
			//Integer [] flightIds = dbcon.getFlightsByReservationId(reservationId);
			while(flightIds[i]!=	 null)
			{
//			 flightStatus[i]= dbcon.getFlightTimes(Integer.valueOf(flightIds[i]));
				flightTime.add(flightStatus[i]);
				
			}
			
			/*customer and reservation --- many flights.. 
			get number of flights ( flight ids from Journey table )
			*/ 
			String status = "";
			for(int j=0; j<flightTime.size(); j++)
			{
				// 				gather all the flight times here and form a STring of *****flightStatus****
				status.concat(flightTime.get(j));
			}
			MessageProducer MP;
			try {
				MP = session.createProducer(topic);
				TextMessage TM = session.createTextMessage(status);
				MP.send(TM);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

}
