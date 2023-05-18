package bankingsystem;

import java.io.*;
import java.sql.*;

public class DBManager {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/bankingATM?allowMultiQueries=true";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password!";

	private static DBManager instance;
	private Connection connection;

	// Default Class Constructor
	private DBManager() {
		// Attempt to Establish Connection with Database
		try {
			// Load the MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Connect to the Database
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("SUCCESS: Connected to Database!");

			// Reset Database
			resetDatabase(connection.createStatement(), "create_users.sql", "Users & Accounts Resetted!");

		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			System.out.println("ERROR: Class NOT Found. Check JAR!");
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("ERROR: Bad SQL Query!");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("ERROR: Issues Encountered at DBManager!");
		}
	}

	// Get Database Instance
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// Close Database Connection
	public void closeConnection() {
		// Attempt to Close Database Connection
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("ERROR: Issues Encountered when Closing Database Connection!");
		}
	}

	// Reset and Initialize Database
	public void resetDatabase(Statement statement, String fileName, String successMessage) throws Exception {
		// Initialize File Location
		String fileLocation = System.getProperty("user.dir") + "\\resources\\scripts\\" + fileName;

		// Read the SQL script that creates the tables
		File file = new File(fileLocation);
		InputStream inputStream = new FileInputStream(file);
		String SQL = new String(inputStream.readAllBytes());
		inputStream.close();

		// Execute the SQL script
		statement.executeUpdate(SQL);

		// Print Success Status
		System.out.println("SUCCESS: " + successMessage);

	}

	// Get Database Connection
	public Connection getConnection() {
		return connection;
	}
}