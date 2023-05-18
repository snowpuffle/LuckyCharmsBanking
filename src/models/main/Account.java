package models.main;

public class Account {
	private int ID;
	private int userID;
	private String accountNumber;
	private String type;
	private double balance;

	// Default Class Constructor
	public Account(int ID, int userID, String accountNumber, String type, double balance) {
		this.ID = ID;
		this.userID = userID;
		this.accountNumber = accountNumber;
		this.type = type;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\tUserID: " + userID + "\t\tBalance: $" + balance + "\tAccountNumber: " + accountNumber
				+ "\tType: " + type;
	}

	// Getter and Setter Methods
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int owner) {
		this.userID = owner;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}