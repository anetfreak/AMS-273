package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

public class SQLConnectionPool {
	private static SQLConnectionPool pool = new SQLConnectionPool();
	private Queue<Connection> conn = new LinkedList<Connection>();
	public static final int POOL_SIZE = 25;
	//private static Connection conn[] = null;
	private SQLConnectionPool()
	{
		Connection con = null;
		//Statement s = null;
		//ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();


			for(int i =0 ; i< POOL_SIZE; i++)
			{
				con = DriverManager.getConnection("jdbc:mysql://localhost/ams_schema",
						"root", "root");

				if (!con.isClosed()) {
					System.out.println("Successfully Connected to Mysql server using TCP/IP");
					conn.add(con);
				}
			}
			//s = con.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static SQLConnectionPool getPool()
	{
		return pool;
	}

	public Connection getConn()
	{
		//if(!conn.isEmpty())
		System.out.println("connection will be given  , size is "+conn.size());
			return conn.poll();
		//return null;
	}

	public void closeConn(Connection c)
	{
		System.out.println("connection pool size is "+conn.size());
		conn.add(c);
		System.out.println("connection released , now size is "+conn.size());
	}
}
