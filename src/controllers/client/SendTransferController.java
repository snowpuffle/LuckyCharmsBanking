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
import models.main.Account;
import models.main.User;

public class SendTransferController implements Initializable {
	// Main Attributes
	private User client;
	private List<Account> listOfAccounts;

	// Transaction Fields
	public ComboBox<String> SenderAccountField;
	public TextField RecipientAccountField;
	public TextField AmountField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Default Class Constructor
	public SendTransferController(User client) {
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
		// Display Account Number for Evey Account Associated with Client
		for (Account account : listOfAccounts) {
			String accountNumber = formatAccountNumber(account.getAccountNumber());
			SenderAccountField.getItems().add(accountNumber);
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
		String senderAccntNumber = SenderAccountField.getValue();
		String recipientAccntNumber = RecipientAccountField.getText().trim();
		String amount = AmountField.getText().trim();

		// Validate Sender and Recipient Account Fields are NOT Empty
		if (validateFields(senderAccntNumber, recipientAccntNumber)) {
			// Validate Amount Value is Greater than 0
			double amountValue = validateAmount(amount);
			if (amountValue > 0) {

				// Retrieve Accounts from Database
				Account senderAccount = findSenderAccount(senderAccntNumber);
				Account recipientAccount = findRecipientAccount(recipientAccntNumber);

				// Validate Accounts
				if (validateAccounts(senderAccount, recipientAccount)) {

					// Handle Transaction
					boolean withdrawn = withdraw(senderAccount, amountValue);
					if (withdrawn) {
						deposit(recipientAccount, amountValue);
					}
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
	private boolean withdraw(Account account, double amount) {
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
				return true;
			} else {
				handleMessageLabel("Cannot Withdraw Funds!", false);
				return false;
			}
		} else {
			handleMessageLabel("Insufficient Funds!", false);
			return false;
		}
	}

	// Find Sender and Recipient Accounts
	private boolean validateAccounts(Account senderAccount, Account recipientAccount) {
		if (senderAccount == null) {
			handleMessageLabel("CANNOT Find Sender Account!", false);
			return false;
		} else if (recipientAccount == null) {
			handleMessageLabel("CANNOT Find Recipient Account!", false);
			return false;
		} else if (senderAccount.getAccountNumber().equals(recipientAccount.getAccountNumber())) {
			handleMessageLabel("Sender Account CANNOT Equal Recipient Account", false);
			return false;
		} else {
			System.out.println(recipientAccount.toString());
		}
		return true;
	}

	// Find Sender Account
	private Account findSenderAccount(String accountNumber) {
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

	// Find Recipient Account
	private Account findRecipientAccount(String accountNumber) {
		List<Account> listOfAllAccounts = Model.getInstance().getAllAccounts();

		// Find the Account that Corresponds with the Account Number
		for (Account account : listOfAllAccounts) {
			String tempAccountNumber = account.getAccountNumber();

			// Account is Found
			if (accountNumber.equals(tempAccountNumber)) {
				return account;
			}
		}
		// Account NOT Found
		return null;
	}

	// Validate Fields
	private boolean validateFields(String senderAccntNumber, String recipientAccntNumber) {
		// Initialize Flag
		boolean success = true;
		// Check if the Fields are Empty
		if (senderAccntNumber == null || senderAccntNumber.isBlank()) {
			success = false;
			handleMessageLabel("Please Select a Sender Account!", false);
		} else if (recipientAccntNumber.isEmpty() || recipientAccntNumber.isBlank()) {
			success = false;
			handleMessageLabel("Please Enter Recipient Account Number!", false);
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
