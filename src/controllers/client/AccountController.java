package controllers.client;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Model;
import models.main.*;

public class AccountController implements Initializable {
	// Main Attributes
	private User client;
	private Account checkingAccount;
	private Account savingsAccount;

	// Main Button Attributes
	public Button AddAccountButton;
	public Button SendTransferButton;
	public Button DepositWithdrawButton;
	public Button GoBackButton;

	// Checking Account Attributes
	public AnchorPane CheckingAccountTile;
	public Label CheckingBalanceField;
	public Label CheckingNumberField;
	public Label CheckingTypeLabel;

	// Savings Account Attributes
	public AnchorPane SavingsAccountTile;
	public Label SavingsBalanceField;
	public Label SavingsNumberField;
	public Label SavingsTypeLabel;

	// Default Class Constructor
	public AccountController(User client) {
		this.client = client;
		this.checkingAccount = null;
		this.savingsAccount = null;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		addListeners();
		initializeFrame();
		setCheckingAccount();
		setSavingsAccount();
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {
		GoBackButton.setOnAction(event -> handleGoBack());
		DepositWithdrawButton.setOnAction(event -> handleDepositWithdraw());
		SendTransferButton.setOnAction(event -> handleSendTransfer());
		AddAccountButton.setOnAction(event -> handleAddAccount());

	}

	// Get Associated Accounts with Client
	private void initializeFrame() {
		// Continue ONLY if Client Exists
		if (client != null) {

			// Get All Accounts Associated with Client
			List<Account> accounts = Model.getInstance().getAllAccountsByUser(client);

			// Save Accounts to this Instance
			if (accounts.size() > 0) {
				for (Account account : accounts) {
					if (account.getType().equals("CHECKING")) {
						this.checkingAccount = account;
					} else if (account.getType().equals("SAVINGS")) {
						this.savingsAccount = account;
					}
				}
			}
		}
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		if (client != null) {
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showClientDashboardFrame(client);
		}
	}

	// Event: "Deposit/Withdraw" Button is Clicked
	private void handleDepositWithdraw() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showDepositWithdrawFrame(client);
	}

	// Event: "Send/Transfer" Button is Clicked
	private void handleSendTransfer() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showSendTransferFrame(client);
	}

	// Event: "Add Account" Button is Clicked
	private void handleAddAccount() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showAddClientAccountFrame(client);
	}

	// Initialize Checking Account Fields
	private void setCheckingAccount() {
		if (checkingAccount != null) {
			String formattedBalance = formatBalance(checkingAccount.getBalance());
			CheckingBalanceField.setText(formattedBalance);
			CheckingNumberField.setText(checkingAccount.getAccountNumber());
		} else {
			CheckingBalanceField.setText("$ --.--");
			CheckingNumberField.setText("**** **** **** 0000");
		}
	}

	// Initialize Savings Account Fields
	private void setSavingsAccount() {
		if (savingsAccount != null) {
			String formattedBalance = formatBalance(savingsAccount.getBalance());
			SavingsBalanceField.setText(formattedBalance);
			SavingsNumberField.setText(savingsAccount.getAccountNumber());
		} else {
			SavingsBalanceField.setText("$ --.--");
			SavingsNumberField.setText("**** **** **** 0000");
		}
	}

	// Format Account Balance to Currency Format
	private String formatBalance(double balance) {
		// Create a NumberFormat instance for the Currency Format
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

		// Set the Currency Symbol to $
		currencyFormat.setCurrency(Currency.getInstance("USD"));

		// Get the Formatted Balance String
		return currencyFormat.format(balance);
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) GoBackButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}