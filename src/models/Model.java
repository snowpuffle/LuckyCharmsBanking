package models;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import bankingsystem.*;
import models.main.*;
import models.seeds.*;
import models.daos.*;
import views.*;

public class Model {
	private static Random random = new Random();

	private static Model model;
	private final ViewFactory viewFactory;;
	private UserDAO userDAO;
	private AccountDAO accountDAO;

	// Default Class Constructor
	private Model() {
		// Set View Factory and DAOs
		this.viewFactory = new ViewFactory();
		this.userDAO = new UserDAO(DBManager.getInstance().getConnection());
		this.accountDAO = new AccountDAO(DBManager.getInstance().getConnection());

		seedUsers();
		seedAccounts();
		printAllUsers();
		printAllAccounts();
	}

	// Get Database Instance
	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	// Get View Factory
	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	// Generate Seed Users
	private void seedUsers() {
		// Instantiate UserSeeds Object to Generate User Data
		UserSeeds seeds = new UserSeeds();

		// Retrieve the List of Users from the UserSeeds Object
		List<User> listOfSeeds = seeds.getListOfUsers();

		// Iterate Over the List of Users and Add Each User to the Database
		for (User user : listOfSeeds) {
			userDAO.addUser(user);
		}
	}

	// Generate Seed Accounts
	private void seedAccounts() {
		// Instantiate AccountSeeds Object to Generate Account Data
		AccountSeeds seeds = new AccountSeeds(getAllUsers());

		// Retrieve the List of Accounts from the AccountSeeds Object
		List<Account> listOfAccounts = seeds.getListOfAccounts();

		// Iterate Over the List of Accounts and Add Each Account to the Database
		for (Account account : listOfAccounts) {
			accountDAO.addAccount(account);
		}
	}

	// Login User
	public User loginUser(String username, String password) {
		return userDAO.findByUserNameAndPassword(username, password);
	}

	// Get All Users from Database
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	// Get All Accounts from Database
	public List<Account> getAllAccounts() {
		return accountDAO.getAllAccounts();
	}

	// Get All Accounts Associated with User from Database
	public List<Account> getAllAccountsByUser(User user) {
		return accountDAO.getAccountsByUser(user);
	}

	// Check Account Type
	public Account checkAccountType(User user, String type, double balance) {
		// Generate a Unique ID for the New Animal
		int ID = generateID();
		String accountNumber = generateAccountNumber();

		// Create a New Account Object
		Account account = new Account(ID, user.getID(), accountNumber, type, balance);

		// Check if Account Already Exists for User
		if (accountDAO.checkAccountType(account)) {
			return account;
		} else {
			return null;
		}
	}

	// Add New Account
	public boolean addNewAccount(User user, String type) throws SQLException {
		// Generate a Unique ID for the New Animal
		int ID = generateID();
		String accountNumber = generateAccountNumber();
		double balance = 10.0;

		// Create New Account Object
		Account account = new Account(ID, user.getID(), accountNumber, type, balance);

		// Add Account and Return Success
		return accountDAO.addAccount(account);
	}

	// Add New Account
	public boolean addNewAccount(Account account) throws SQLException {
		// Add Account and Return Success
		return accountDAO.addAccount(account);
	}

	// Update Account with New Balance
	public boolean updateAccountBalance(Account account) {
		return accountDAO.updateAccountBalance(account);
	}

	// Change User Password
	public boolean updateUserPassword(User user, String newPassword) {
		return userDAO.updatePassword(user, newPassword);
	}

	// Print All Users
	private void printAllUsers() {
		List<User> listOfUsers = getAllUsers();
		for (User user : listOfUsers) {
			System.out.println(user.toString());
		}
	}

	// Print All Accounts
	private void printAllAccounts() {
		List<Account> listOfAccounts = getAllAccounts();
		for (Account account : listOfAccounts) {
			System.out.println(account.toString());
		}
	}

	// Generate Random ID
	private int generateID() {
		int minID = 10000;
		int maxID = 99999;

		return random.nextInt(maxID - minID + 1) + minID;
	}

	// Generate Random Account Number
	private String generateAccountNumber() {
		StringBuilder accountNumber = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			if (i > 0 && i % 4 == 0) {
				accountNumber.append(" ");
			}
			int num = random.nextInt(10);
			accountNumber.append(num);
		}
		return accountNumber.toString();
	}
}