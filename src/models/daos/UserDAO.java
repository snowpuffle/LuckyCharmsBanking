package models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.main.*;

public class UserDAO {
	private final Connection connection;

	// SQL Queries
	private static final String GET_ALL_USERS = "SELECT * FROM Users";
	private static final String GET_BY_USERNAME_PASSWORD = "SELECT * FROM Users WHERE username = ? AND password = ?";
	private static final String ADD_USER = "INSERT INTO Users (ID, FirstName, LastName, Username, Password, Gender, ImageURL, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_USER = "UPDATE Users SET Username = ?, ImageURL = ? WHERE ID = ?";
	private static final String UPDATE_PASSWORD = "UPDATE Users SET password = ? WHERE ID = ?";

	// Default Class Constructor
	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	// Get All Users from Database
	public List<User> getAllUsers() {
		// Initialize Empty User List
		List<User> users = new ArrayList<>();

		// Initialize SQL Components
		PreparedStatement statement = null;
		ResultSet results = null;

		// Attempt to Get All Users from Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(GET_ALL_USERS);

			// Execute Statement and Handle Results
			results = statement.executeQuery();
			while (results.next()) {
				User user = addUserFromResultSet(results);
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("ERROR USERDAO: Cannot Get Users! " + e);
		}

		// Return List of Users
		return users;
	}

	// Find User by UserName and Password
	public User findByUserNameAndPassword(String username, String password) {
		// Initialize Empty User
		User user = null;

		// Initialize SQL Component
		PreparedStatement statement = null;

		// Attempt to Add User to Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(GET_BY_USERNAME_PASSWORD);
			statement.setString(1, username);
			statement.setString(2, password);

			// Execute Statement
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int ID = resultSet.getInt("ID");
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String gender = resultSet.getString("Gender");
				String type = resultSet.getString("Type");
				String imageURL = resultSet.getString("ImageURL");
				user = new User(ID, firstName, lastName, username, password, gender, imageURL, type);
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.out.println("ERROR USERDAO: Cannot Find User! " + e);
		}

		return user;
	}

	// Get User From Result Set
	private User addUserFromResultSet(ResultSet results) {
		// Initialize Empty User
		User user = null;

		// Attempt to Extract User Attributes from ResultSet
		try {
			int id = results.getInt("ID");
			String firstName = results.getString("FirstName");
			String lastName = results.getString("LastName");
			String username = results.getString("UserName");
			String password = results.getString("Password");
			String gender = results.getString("Gender");
			String type = results.getString("Type");
			String imageURL = results.getString("ImageURL");

			user = new User(id, firstName, lastName, username, password, gender, imageURL, type);

		} catch (SQLException e) {
			System.out.println("ERROR USERDAO: Cannot Extract User Attributes from ResultSet! " + e);
		}

		// Return User
		return user;
	}

	// Add User to Database
	public boolean addUser(User user) {
		// Initialize Flag
		boolean success = true;

		// Initialize SQL Component
		PreparedStatement statement = null;

		// Attempt to Add User to Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(ADD_USER);
			statement.setInt(1, user.getID());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getUsername());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getGender());
			statement.setString(7, user.getImageURL());
			statement.setString(8, user.getType());

			// Execute Statement
			statement.executeUpdate();

		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR USERDAO: Cannot Add User! " + e);
		}

		// Return Flag
		return success;
	}

	// Update Existing User from Database
	public boolean updateUser(User user) {
		// Initialize Flag
		boolean success = true;

		// Initialize SQL Component
		PreparedStatement statement = null;

		// Attempt to Add User to Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(UPDATE_USER);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getImageURL());
			statement.setInt(3, user.getID());

			// Execute Statement
			statement.executeUpdate();

		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR USERDAO: Cannot Update User! " + e);
		}

		// Return Flag
		return success;
	}

	// Update User Password from Database
	public boolean updatePassword(User user, String newPassword) {
		// Initialize Flag
		boolean success = true;

		// Initialize SQL Component
		PreparedStatement statement = null;

		// Attempt to Add User to Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(UPDATE_PASSWORD);
			statement.setString(1, newPassword);
			statement.setInt(2, user.getID());

			// Execute Statement
			statement.executeUpdate();

		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR USERDAO: Cannot Update User Password! " + e);
		}

		// Return Flag
		return success;
	}
}