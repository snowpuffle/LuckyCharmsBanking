package models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.main.*;

public class AccountDAO {
	private final Connection connection;

	// SQL Queries
	private static final String GET_ALL_ACCOUNTS = "SELECT * FROM Accounts";
	private static final String GET_ALL_ACCOUNTS_BY_USER = "SELECT * FROM Accounts WHERE UserID = ?";
	private static final String ADD_ACCOUNT = "INSERT INTO Accounts (ID, UserID, AccountNumber, Type, Balance) VALUES (?, ?, ?, ?, ?)";
	private static final String CHECK_ACCOUNT_TYPE = "SELECT * FROM Accounts WHERE UserID = ? AND Type = ?";
	private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE Accounts SET Balance = ? WHERE ID = ?";

	// Default Class Constructor
	public AccountDAO(Connection connection) {
		this.connection = connection;
	}

	// Get All Users from Database
	public List<Account> getAllAccounts() {
		// Initialize Empty User List
		List<Account> accounts = new ArrayList<>();

		// Initialize SQL Components
		PreparedStatement statement = null;
		ResultSet results = null;

		// Attempt to Get All Users from Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(GET_ALL_ACCOUNTS);

			// Execute Statement and Handle Results
			results = statement.executeQuery();
			while (results.next()) {
				Account account = addAccountFromResultSet(results);
				accounts.add(account);
			}
		} catch (SQLException e) {
			System.out.println("ERROR ACCOUNTDAO: Cannot Get Users! " + e);
		}

		// Return List of Users
		return accounts;
	}

	// Get All Accounts Associated with User
	public List<Account> getAccountsByUser(User user) {
		// Initialize Empty User List
		List<Account> accounts = new ArrayList<>();

		// Initialize SQL Components
		PreparedStatement statement = null;
		ResultSet results = null;

		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(GET_ALL_ACCOUNTS_BY_USER);
			statement.setInt(1, user.getID());

			// Execute Statement and Handle Results
			results = statement.executeQuery();
			while (results.next()) {
				Account account = addAccountFromResultSet(results);
				accounts.add(account);
			}
		} catch (SQLException e) {
			System.out.println("ERROR ACCOUNTDAO: Cannot Retrieve Accounts for User: " + e.getMessage());
		}
		return accounts;
	}

	// Get User From Result Set
	private Account addAccountFromResultSet(ResultSet results) {
		// Initialize Empty User
		Account account = null;

		// Attempt to Extract User Attributes from ResultSet
		try {
			int id = results.getInt("ID");
			int userID = results.getInt("UserID");
			String accountNumber = results.getString("AccountNumber");
			String type = results.getString("Type");
			double balance = results.getDouble("Balance");

			account = new Account(id, userID, accountNumber, type, balance);

		} catch (SQLException e) {
			System.out.println("ERROR ACCOUNTDAO: Cannot Extract Account Attributes from ResultSet! " + e);
		}

		// Return User
		return account;
	}

	// Add Account to Database
	public boolean addAccount(Account account) {
		// Initialize Flag
		boolean success = true;

		if (checkAccountType(account)) {
			// Initialize SQL Component
			PreparedStatement statement = null;

			// Attempt to Add Account to Database
			try {
				// Prepare Statement and Set Values
				statement = connection.prepareStatement(ADD_ACCOUNT);
				statement.setInt(1, account.getID());
				statement.setInt(2, account.getUserID());
				statement.setString(3, account.getAccountNumber());
				statement.setString(4, account.getType());
				statement.setDouble(5, account.getBalance());

				// Execute Statement
				statement.executeUpdate();

			} catch (SQLException e) {
				success = false;
				System.out.println("ERROR ACCOUNTDAO: Cannot Add Account! " + e);
			}
		} else {
			success = false;
		}

		// Return Flag
		return success;
	}

	// Check if User Already has a Checking Account and Savings Account
	public boolean checkAccountType(Account account) {
		// Initialize Flag
		boolean success = true;

		// Initialize SQL Component
		PreparedStatement statement = null;
		ResultSet results = null;

		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(CHECK_ACCOUNT_TYPE);
			statement.setInt(1, account.getUserID());
			statement.setString(2, account.getType());

			// Execute Statement and Handle Results
			results = statement.executeQuery();
			if (results.next()) {
				System.out.println("ERROR: User Already Has a " + account.getType() + " Account!");
				success = false;
			}
		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR ACCOUNTDAO: Cannot Check Account Type! " + e);
		}

		return success;
	}

	public boolean updateAccountBalance(Account account) {
		// Initialize Flag
		boolean success = true;

		// Initialize SQL Component
		PreparedStatement statement = null;

		// Attempt to Update Account Balance in the Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE);
			statement.setDouble(1, account.getBalance());
			statement.setInt(2, account.getID());

			// Execute Statement
			statement.executeUpdate();
		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR ACCOUNTDAO: Cannot Update Account Balance! " + e);
		}

		// Return Flag
		return success;
	}

}