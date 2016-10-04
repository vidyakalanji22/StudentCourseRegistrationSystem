package com.scr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnectionManager {
	private static BasicDataSource connectionPool = null;
	private static Properties confProperties = PropertyLoader.getConfProperties();
	
	public static void initialize(){
		/*connectionPool = new BasicDataSource(); 
		connectionPool.setDriverClassName(confProperties.getProperty("db.driver"));
		connectionPool.setUrl(confProperties.getProperty("db.url"));
		connectionPool.setUsername(confProperties.getProperty("db.username"));
		connectionPool.setPassword(confProperties.getProperty("db.password"));
		connectionPool.setTestOnBorrow(true);
		connectionPool.setValidationQuery(confProperties.getProperty("db.validation.query"));
		connectionPool.setInitialSize(Integer.parseInt("100"));
		connectionPool.setDefaultTransactionIsolation(2);*/
		
	}

	public static Connection getConnection() throws Exception {
		Connection connection = null;
		/*if(connectionPool == null){
			initialize();
		}*/
		//connection = connectionPool.getConnection();
		
		Class.forName("org.postgresql.Driver");

		System.out.println("Connecting to Database...");

		// Get Connection to database
		connection = DriverManager.getConnection(confProperties.getProperty("db.url"),
				confProperties.getProperty("db.username"), confProperties.getProperty("db.password"));
		connection.setTransactionIsolation(2);
		
		return connection;
	}


	public static void close(Connection connection, Statement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *
	 * @param query
	 *            String The query to be executed
	 * @return a ResultSet object containing the results or null if not
	 *         available
	 * @throws SQLException
	 */
	public ResultSet query(String query) throws SQLException {
		ResultSet resultSet = null;
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		resultSet = preparedStatement.executeQuery(query);
		return resultSet;
	}

	/**
	 * Method to insert data to a table
	 * 
	 * @param insertQuery
	 *            String The Insert query
	 * @return boolean
	 * @throws SQLException
	 */
	public int insert(String insertQuery) throws SQLException {
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
		int result = preparedStatement.executeUpdate(insertQuery);
		return result;

	}

}
