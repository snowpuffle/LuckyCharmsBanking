package models.seeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.main.*;

public class AccountSeeds {
	private List<Account> listOfAccounts;
	private static Random random = new Random();

	// Default Class Constructor
	public AccountSeeds(List<User> listOfUsers) {
		this.listOfAccounts = new ArrayList<Account>();
		generateClientAccounts(listOfUsers, "CHECKING");
		generateClientAccounts(listOfUsers, "SAVINGS");
		
	}

	// Generate Random Accounts for Clients
	private void generateClientAccounts(List<User> listOfUsers, String type) {
		// Generate an Account for Every Client User
		for (User user : listOfUsers) {
			if ("CLIENT".equalsIgnoreCase(user.getType())) {
				int ID = generateID();
				int userID = user.getID();
				String accountNumber = generateAccountNumber();
				double balance = generateRandomBalance();

				Account account = new Account(ID, userID, accountNumber, type, balance);
				this.listOfAccounts.add(account);
			}
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

	// Generate Random Balance
	private double generateRandomBalance() {
		double minBalance = 100;
		double maxBalance = 10000;

		return random.nextDouble(maxBalance - minBalance + 1) + maxBalance;
	}

	// Get List of Accounts
	public List<Account> getListOfAccounts() {
		return this.listOfAccounts;
	}
}