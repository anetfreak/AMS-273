package com.dao;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class JMSClient implements MessageListener{

	public static void main(String[] args) {
	
		

	}

	public void onMessage(Message message) {
		TextMessage TM = (TextMessage)message;
		try{
		
		System.out.println(TM.getText());
		}
		catch(JMSException j)
		{
			j.printStackTrace();
		}
		
	}

}
