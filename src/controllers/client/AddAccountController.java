package controllers.client;

import java.net.URL;
import java.sql.SQLException;
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

public class AddAccountController implements Initializable {
	// Main Attributes
	private User client;

	// New Account Attributes
	public ComboBox<String> AccountTypeField;
	public TextField BalanceField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Default Class Constructor
	public AddAccountController(User client) {
		this.client = client;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Frame
		initializeFrame();

		// Initialize OnClick Action of Buttons
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleAddAccount());
	}

	// Initialize Frame Attributes
	private void initializeFrame() {
		AccountTypeField.getItems().addAll("Checking", "Savings");
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		if (client != null) {
			closeCurrentWindow();
			Model.getInstance().getViewFactory().showClientDashboardFrame(client);
		}
	}

	// Event: "Submit" Button is Clicked
	private void handleAddAccount() {
		// Retrieve Account Attributes
		String accountType = AccountTypeField.getValue();
		String balance = BalanceField.getText().trim();

		// Validate Account Type
		if (validateType(accountType)) {

			// Continue ONLY if Balance is Valid
			double balanceValue = validateBalance(balance);
			if (balanceValue > 0) {

				// Check if Account Exists for this User
				Account account = checkAccountType(accountType, balanceValue);

				// Continue ONLY if Account Exists
				if (account != null) {
					try {
						Model.getInstance().addNewAccount(account);
						handleMessageLabel("Account Successfully Added!", true);
					} catch (SQLException e) {
						handleMessageLabel("Cannot Add Account!", false);
					}
				} else {
					handleMessageLabel("User Already Has a " + accountType + " Account!", false);
				}
			}
		}
	}

	// Check if Account Exists
	private Account checkAccountType(String accountType, double balance) {
		return Model.getInstance().checkAccountType(client, accountType, balance);
	}

	// Validate Type Field
	private boolean validateType(String accountType) {
		// Initialize Flag
		boolean success = true;

		// Check if the Fields are Empty
		if (accountType == null || accountType.isBlank()) {
			success = false;
			handleMessageLabel("Please Select an Account Type!", false);
		}

		return success;
	}

	// Validate and Convert Balance to Double
	private double validateBalance(String balance) {
		// Initialize Balance Value
		double balanceValue = -1;

		// Attempt to Validate and Convert Balance
		try {
			// Check if the Fields are Empty
			if (balance.isBlank()) {
				handleMessageLabel("Please Enter a Balance!", false);
			} else {
				// Convert Balance to Double
				balanceValue = Double.parseDouble(balance);
			}
		} catch (NumberFormatException e) {
			handleMessageLabel("Please Enter a Valid Balance!", false);
		}

		// Return Balance Value
		return balanceValue;
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