package controllers.client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.main.*;

public class DepositWithdrawController implements Initializable {
	// Main Attributes
	private User client;
	private List<Account> listOfAccounts;

	// Transaction Fields
	public ComboBox<String> AccountField;
	public ComboBox<String> TransactionTypeField;
	public TextField AmountField;
	public TextField UsernameField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Default Class Constructor
	public DepositWithdrawController(User client) {
		this.client = client;
		listOfAccounts = Model.getInstance().getAllAccountsByUser(client);
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Frame
		initializeFrame();

		// Initialize OnClick Action of Buttons
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleSubmit());
	}

	// Initialize Frame Attributes
	private void initializeFrame() {
		// Initialize Username and Transaction Type Fields
		UsernameField.setText(client.getUsername());
		TransactionTypeField.getItems().addAll("Deposit", "Withdraw");

		// Display Account Number for Evey Account Associated with Client
		for (Account account : listOfAccounts) {
			String accountNumber = formatAccountNumber(account.getAccountNumber());
			AccountField.getItems().add(accountNumber);
		}
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		if (client != null) {
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showManageAccountsFrame(client);
		}
	}

	// Event: "Submit" Button is Clicked
	private void handleSubmit() {
		// Retrieve Field Values
		String transactionType = TransactionTypeField.getValue();
		String accountNumber = AccountField.getValue();
		String amount = AmountField.getText().trim();

		// Validate Transaction Type and Account Number
		if (validateFields(transactionType, accountNumber)) {

			// Validate and Get Account and Amount Value
			Account account = findAccount(accountNumber);
			double amountValue = validateAmount(amount);

			// Continue ONLY if Account Exists and Amount is Valid
			if (account != null && amountValue > 0) {

				// Handle Transaction by Type
				if ("DEPOSIT".equalsIgnoreCase(transactionType)) {
					deposit(account, amountValue);
				} else if ("WITHDRAW".equalsIgnoreCase(transactionType)) {
					withdraw(account, amountValue);
				}
			}
		}
	}

	// Handle Deposit Transaction
	private void deposit(Account account, double amount) {
		// Calculate New Balance
		double balance = account.getBalance();
		double newBalance = balance + amount;
		account.setBalance(newBalance);

		// Update Balance to Database using Model
		boolean success = Model.getInstance().updateAccountBalance(account);
		if (success) {
			handleMessageLabel("Funds Successfully Added!", true);
		} else {
			handleMessageLabel("Cannot Add Funds!", false);
		}
	}

	// Handle Withdraw Transaction
	private void withdraw(Account account, double amount) {
		// Calculate New Balance
		double balance = account.getBalance();
		double newBalance = balance - amount;

		// Check if the withdrawal amount is valid
		if (newBalance >= 0) {
			account.setBalance(newBalance);

			// Update Balance to Database using Model
			boolean success = Model.getInstance().updateAccountBalance(account);
			if (success) {
				handleMessageLabel("Funds Successfully Withdrawn!", true);
			} else {
				handleMessageLabel("Cannot Withdraw Funds!", false);
			}
		} else {
			handleMessageLabel("Insufficient Funds!", false);
		}
	}

	// Find Account Selected by User
	private Account findAccount(String accountNumber) {
		// Extract the Last 4 Digits of Account Number
		String lastFourDigits = accountNumber.substring(accountNumber.length() - 4);

		// Find the Account that Corresponds with the 4 Digits
		for (Account account : listOfAccounts) {
			String accountNum = account.getAccountNumber();
			String accountLastFourDigits = accountNum.substring(accountNum.length() - 4);

			// Account is Found
			if (lastFourDigits.equals(accountLastFourDigits)) {
				return account;
			}
		}
		// Account NOT Found
		return null;
	}

	// Validate Fields
	private boolean validateFields(String transactionType, String accountNumber) {
		// Initialize Flag
		boolean success = true;
		// Check if the Fields are Empty
		if (transactionType == null || transactionType.isBlank()) {
			success = false;
			handleMessageLabel("Please Select a Transaction Type!", false);
		} else if (accountNumber == null || accountNumber.isBlank()) {
			success = false;
			handleMessageLabel("Please Select an Account!", false);
		}
		return success;
	}

	// Validate and Convert Amount to Double
	private double validateAmount(String amount) {
		// Initialize Balance Value
		double amountValue = -1;

		// Attempt to Validate and Convert Balance
		try {
			// Check if the Fields are Empty
			if (amount.isBlank()) {
				handleMessageLabel("Please Enter an Amount!", false);
			} else {
				// Convert Balance to Double
				amountValue = Double.parseDouble(amount);

				// Check if Amount is Under $5,000
				if (amountValue > 5000) {
					handleMessageLabel("Amount Must Be Under $5000!", false);
					amountValue = -1; // Reset amountValue to Indicate an Invalid Amount
				}
			}
		} catch (NumberFormatException e) {
			handleMessageLabel("Please Enter a Valid Amount!", false);
		}

		// Return Balance Value
		return amountValue;
	}

	// Display Formatted Account Number
	private String formatAccountNumber(String accountNumber) {
		String formattedNumber = "**** **** **** ";
		if (accountNumber.length() < 16 || accountNumber == null) {
			formattedNumber += "1234";
		} else {
			String lastFourDigits = accountNumber.substring(accountNumber.length() - 4);
			formattedNumber += lastFourDigits;
		}
		return formattedNumber;
	}

	// Handle Error
	private void handleMessageLabel(String message, boolean success) {
		if (success) {
			MessageLabel.setText("SUCCESS: " + message);
			MessageLabel.setTextFill(Color.GREEN); // Set Text Color to Green for Success
		} else {
			MessageLabel.setText("ERROR: " + message);
			MessageLabel.setTextFill(Color.RED); // Set Text Color to Red for Error
		}
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) SubmitButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}